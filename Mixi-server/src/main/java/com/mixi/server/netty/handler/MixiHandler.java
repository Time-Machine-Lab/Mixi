package com.mixi.server.netty.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.protocol.AccessMessage;

import java.rmi.RemoteException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:45
 */
public interface MixiHandler {

    Object handle(MixiNettyChannel channel, AccessMessage message) throws RemoteException;

    default void processEvent(MixiNettyChannel channel, AccessMessage message) throws RemoteException {
        handle(channel, message);
    }

}
