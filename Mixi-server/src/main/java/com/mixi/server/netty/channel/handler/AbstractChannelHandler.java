package com.mixi.server.netty.channel.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;
import lombok.NonNull;

import java.rmi.RemoteException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/5 19:47
 */
public abstract class AbstractChannelHandler implements ChannelHandler{

    protected final ChannelHandler handler;

    protected AbstractChannelHandler(@NonNull ChannelHandler handler) {
        this.handler = handler;
    }

    @Override
    public void connect(MixiNettyChannel channel) {
        handler.connect(channel);
    }

    @Override
    public void disconnect(MixiNettyChannel channel) {
        handler.disconnect(channel);
    }

    @Override
    public void send(MixiNettyChannel channel, Object message) {
        handler.send(channel, message);
    }

    @Override
    public void receive(MixiNettyChannel channel, Object message) {
        handler.receive(channel, message);
    }

    @Override
    public void caught(MixiNettyChannel channel, Throwable exception) {
        handler.caught(channel, exception);
    }
}
