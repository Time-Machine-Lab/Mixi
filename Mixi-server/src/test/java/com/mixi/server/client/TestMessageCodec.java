package com.mixi.server.client;

import com.mixi.server.netty.NettyServerHandler;
import com.mixi.server.netty.codec.MessageCodec;
import com.mixi.server.netty.codec.ServerMessageWebSocketDecoder;
import com.mixi.server.netty.codec.ServerMessageWebSocketEncoder;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.utils.VarInt;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/27 14:16
 */
@SpringBootTest
public class TestMessageCodec {
    private static SocketChannel channel;
    private static URI uri;
    public static void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyClientHandler())
                                    .addLast("encoder",new ServerMessageWebSocketEncoder())
                                    .addLast("decoder",new ServerMessageWebSocketDecoder());
                        }
                    });

            // Connect to the server
            ChannelFuture cf = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
            channel=(SocketChannel)cf.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class NettyClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("Server response: " + msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println("client connect success!");
            AccessMessage message = new AccessMessage();
            message.setBody("test codec....".getBytes());
            channel.writeAndFlush(message);
        }
    }

    public static void main(String[] args) {
//        try {
//            uri = new URI("ws://localhost:8090/chat");
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        start();
        AccessMessage message = new AccessMessage();
        message.setBody("test codec....".getBytes());
        System.out.println(message);
    }
}
