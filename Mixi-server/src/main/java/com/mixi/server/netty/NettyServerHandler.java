package com.mixi.server.netty;

import com.mixi.server.netty.protocol.AccessMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/24 10:11
 */
//复用管道处理器，做统一处理
@io.netty.channel.ChannelHandler.Sharable
public class NettyServerHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Server received: " + msg);
        ctx.write("ok!");
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("new connect to build!");
        AccessMessage message = new AccessMessage();
        message.setBody("connect finished".getBytes());
        ctx.channel().writeAndFlush(message);
    }
}
