package com.mixi.server.netty.channel.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.util.AccessMessageUtils;
import lombok.NonNull;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/24 22:44
 */
public class LoginAuthHandler extends AbstractChannelHandler{
    protected LoginAuthHandler(@NonNull ChannelHandler handler) {
        super(handler);
    }

    @Override
    public void send(MixiNettyChannel channel, Object message) {

    }

    @Override
    public void receive(MixiNettyChannel channel, Object message) {
    }

}
