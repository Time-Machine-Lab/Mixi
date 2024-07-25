package com.mixi.server.netty;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.support.ChannelAttrs;
import com.mixi.server.netty.channel.handler.ChannelHandler;
import com.mixi.server.util.NetUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/24 10:11
 */
//复用管道处理器，做统一处理
@io.netty.channel.ChannelHandler.Sharable
public class NettyServerHandler extends ChannelDuplexHandler {

    private Logger log = LoggerFactory.getLogger(NettyServerHandler.class);
    private final ChannelHandler handler;

    public NettyServerHandler(ChannelHandler handler) {
        this.handler = handler;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 握手超时事件
        if (evt instanceof WebSocketServerProtocolHandler.ServerHandshakeStateEvent) {
            WebSocketServerProtocolHandler.ServerHandshakeStateEvent event = (WebSocketServerProtocolHandler.ServerHandshakeStateEvent) evt;
            if (event == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_TIMEOUT) {
                log.warn("The client({}) handshake was timed out, the channel is about to close.",
                        NetUtil.toSocketAddressString((InetSocketAddress) ctx.channel().remoteAddress()));
                ctx.close();
            }
        }
        // 握手完成事件
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete event = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            InetSocketAddress remoteAddr = NetUtils.getWsRemoteAddrFromHeader(event.requestHeaders(), ctx.channel());
            initChannel(ctx.channel(), remoteAddr);
            log.info("The client({}) handshake was completed successfully and the channel was upgraded to websockets.",
                    NetUtil.toSocketAddressString((InetSocketAddress)ctx.channel().remoteAddress()));
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        MixiNettyChannel channel = MixiNettyChannel.registerChannelIfAbsent(ctx.channel());
        handler.send(channel, msg);
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MixiNettyChannel channel = MixiNettyChannel.registerChannelIfAbsent(ctx.channel());
        handler.receive(channel,msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("new connect to build!");
        log.info("current node save the channel nums:"+MixiNettyChannel.getAllChannels().size());
        super.channelActive(ctx);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MixiNettyChannel channel = MixiNettyChannel.registerChannelIfAbsent(ctx.channel());
        try {
            log.info("The connection of {} -> {} is disconnected, channelId={}", channel.getRemoteAddress(), channel.getLocalAddress(), channel.getChannelId());
            handler.disconnect(channel);
        } finally {
            MixiNettyChannel.removeChannel(ctx.channel());
        }
    }
    private MixiNettyChannel initChannel(Channel ch, InetSocketAddress remoteAddr) throws Exception {
        if (remoteAddr == null) {
            remoteAddr = (InetSocketAddress) ch.remoteAddress();
        }
        ChannelAttrs.init(ch, remoteAddr);
        MixiNettyChannel channel = MixiNettyChannel.registerChannelIfAbsent(ch);
        log.info("The connection of {} -> {} is established, channelId={}", channel.getRemoteAddress(), channel.getLocalAddress(), channel.getChannelId());
        return channel;
    }
}
