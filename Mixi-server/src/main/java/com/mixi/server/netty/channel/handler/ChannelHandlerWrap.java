package com.mixi.server.netty.channel.handler;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/5 19:46
 */
public class ChannelHandlerWrap {

    private static final ChannelHandlerWrap INSTANCE = new ChannelHandlerWrap();
    private static ChannelHandlerWrap getInstance() {
        return INSTANCE;
    }
    public static ChannelHandler wrap(ChannelHandler handler) {
        return getInstance().wrapHandler(handler);
    }

    private ChannelHandler wrapHandler(ChannelHandler handler) {
        return new IdleChannelHandler(handler);
    }
}
