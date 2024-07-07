package com.mixi.server.netty.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.handler.ChannelHandler;
import com.mixi.server.netty.protocol.AccessMessage;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:48
 */
public class CenterHandler implements ChannelHandler {
    @Override
    public void connect(MixiNettyChannel channel) {

    }

    @Override
    public void disconnect(MixiNettyChannel channel) {

    }

    @Override
    public void send(MixiNettyChannel channel, Object msg) {

    }

    @Override
    public void receive(MixiNettyChannel channel, Object msg) {
        AccessMessage message = (AccessMessage) msg;
    }

    @Override
    public void caught(MixiNettyChannel channel, Throwable throwable) {

    }


}
