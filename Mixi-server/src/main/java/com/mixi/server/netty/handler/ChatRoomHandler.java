package com.mixi.server.netty.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.handler.AbstractChannelHandler;
import com.mixi.server.netty.channel.handler.ChannelHandler;
import com.mixi.server.netty.protocol.AccessMessage;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 18:11
 */
@Component
public class ChatRoomHandler extends MixiAbstractHandler {

    @Override
    protected Object doHandle(MixiNettyChannel channel, AccessMessage message) {
        return null;
    }

}
