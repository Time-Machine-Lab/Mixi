package com.mixi.server.core.ack;

import com.mixi.server.core.concurrent.RoomConsumerPool;
import com.mixi.server.core.store.MixiTimelineStore;
import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.RoomChannelManager;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.protocol.ChatroomMsg;
import com.mixi.server.pojo.VO.TimelineMessage;
import com.mixi.server.util.AccessMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/18 22:05
 */
@Component
@Slf4j
public class AckMessageHandler {

    private MixiTimelineStore timelineStore;
    @Autowired
    public AckMessageHandler(MixiTimelineStore timelineStore){
        this.timelineStore = timelineStore;
    }
    public void consumerAck(MixiNettyChannel channel){
        String roomId = RoomChannelManager.getRoomId(channel.getChannelId());
        List<TimelineMessage> messages = timelineStore.consumeMsgIfNeed(channel.getChannelId(), roomId);
        if(Objects.nonNull(messages)){
            for (TimelineMessage message : messages) {
                ChatroomMsg chatroomMsg = ChatroomMsg.convertMsgToChatRoom(message);
                AccessMessage res = AccessMessageUtils.createChatRoomResponse(chatroomMsg);
                channel.send(res);
            }
        }
    }

}
