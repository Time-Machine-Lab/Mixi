package com.mixi.server.netty.handler;


import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.protocol.AccessMessage;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:46
 */
public abstract class MixiAbstractHandler implements MixiHandler{

    @Override
    public Object handle(MixiNettyChannel channel, AccessMessage message) {
        return doHandle(channel,message);
    }

    protected abstract Object doHandle(MixiNettyChannel channel, AccessMessage message);
}
