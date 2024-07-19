package com.mixi.server.core.chatroom;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.protocol.ChatroomMsg;
import com.mixi.server.pojo.VO.TimelineMessage;

import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/16 19:06
 */
public interface MixiTimeline {

    void push(TimelineMessage message);

    void registerConsumer(String roomId);

    void removeConsumer(String roomId);
}
