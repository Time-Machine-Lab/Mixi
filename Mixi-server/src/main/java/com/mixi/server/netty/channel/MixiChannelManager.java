package com.mixi.server.netty.channel;

import java.net.InetSocketAddress;
import java.rmi.RemoteException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/1 12:31
 */
public interface MixiChannelManager {

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

    boolean isConnected();

    void send(Object message) throws RemoteException;

    void close();

    boolean isClosed();

    boolean hasAttribute(String key);

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    Object removeAttribute(String key);
}
