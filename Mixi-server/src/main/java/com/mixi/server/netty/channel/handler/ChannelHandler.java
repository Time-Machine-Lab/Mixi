package com.mixi.server.netty.channel.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;

import java.rmi.RemoteException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/30 23:25
 */
public interface ChannelHandler {

    void connect(MixiNettyChannel channel);
    void disconnect(MixiNettyChannel channel);
    void send(MixiNettyChannel channel,Object msg);
    void receive(MixiNettyChannel channel,Object msg);
    void caught(MixiNettyChannel channel,Throwable throwable);

}
