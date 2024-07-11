package com.mixi.server.netty.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mixi.common.constant.constpool.RedisCacheKey;
import com.mixi.server.common.Constants;
import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.RoomChannelManager;
import com.mixi.server.netty.channel.support.ChannelAttrs;
import com.mixi.server.netty.protocol.*;
import com.mixi.server.util.AccessMessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.mixi.server.netty.protocol.AccessResponse.*;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 18:11
 */
@Component
public class ChatRoomHandler extends MixiAbstractHandler {

    @Override
    protected Object doHandle(MixiNettyChannel channel, AccessMessage message) {
        String header = AccessMessageUtils.extractHeaderData(message, HeaderEnum.CHATROOM);
        JSONObject jsonObject = JSON.parseObject(header);
        String roomId = jsonObject.getString(Constants.CHATROOM_ID);
        if(StringUtils.isNotBlank(roomId)){
            return INVALID_ROOM_NAME;
        }
        Integer cmd = jsonObject.getInteger(Constants.CHATROOM_CMD);
        if(CommandEnum.CHATROOM_JOIN.getCode() == cmd){
            return joinRoom(channel,message,jsonObject);
        }else if(CommandEnum.CHATROOM_SEND.getCode() == cmd){
            return sendRoomMessage(channel,message,jsonObject);
        }else if (CommandEnum.CHATROOM_LEAVE.getCode() == cmd){
            return leaveRoom(channel,message,jsonObject);
        }
        return null;
    }

    @Override
    public int getMark() {
        return 1;
    }

    private AccessResponse joinRoom(MixiNettyChannel channel,AccessMessage message,JSONObject headerObj){
        ChannelAttrs attrs = channel.getAttrs();
        String uid = headerObj.getString(Constants.CHATROOM_UID);
        if(StringUtils.isBlank(uid)){
            return INVALID_USER_UID;
        }
        //TODO：验证uid合法性
        if(!uid.equals(attrs.getUid())&&attrs.isEnter()){
            return JOIN_ROOM_INVALID;
        }
        RoomChannelManager.addChannel(headerObj.getString("room"),channel,uid);
        //发送消息
        return SUCCESS;
    }

    private AccessResponse sendRoomMessage(MixiNettyChannel channel,AccessMessage message,JSONObject headerObj){
        ChannelAttrs attrs = channel.getAttrs();
        boolean enter = attrs.isEnter();
        if(!enter){
            return NOT_IN_ROOM_WHEN_SEND;
        }
        String uid = attrs.getUid();
        if(!StringUtils.isNotBlank(uid)){
            return EMPTY_UID;
        }
        String body = new String(message.getBody());
        ChatroomMsg request = JSON.parseObject(body, ChatroomMsg.class);
        //发送消息
        return SUCCESS;
    }

    private AccessResponse leaveRoom(MixiNettyChannel channel,AccessMessage message,JSONObject headerObj){
        ChannelAttrs attrs = channel.getAttrs();
        String uid = attrs.getUid();
        if(StringUtils.isBlank(uid)||!uid.equals(attrs.getUid()))
            return INVALID_USER_UID;
        String roomId = headerObj.getString(Constants.CHATROOM_ID);
        String owner = "";
        if(uid.equals(owner)){
            RoomChannelManager.destroyRoom(roomId);
            return SUCCESS;
        }
        RoomChannelManager.removeChannel(roomId,channel);
        //发送消息
        return SUCCESS;
    }

}
