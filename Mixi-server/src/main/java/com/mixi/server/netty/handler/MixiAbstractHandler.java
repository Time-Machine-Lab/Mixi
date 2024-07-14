package com.mixi.server.netty.handler;


import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.protocol.HeaderEnum;
import com.mixi.server.util.AccessMessageUtils;
import org.springframework.util.Assert;

import java.rmi.RemoteException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:46
 */
public abstract class MixiAbstractHandler implements MixiHandler{

    @Override
    public Object handle(MixiNettyChannel channel, AccessMessage message) {
        doHandle(channel,message);
        return null;
    }

    protected abstract Object doHandle(MixiNettyChannel channel, AccessMessage message);
}
