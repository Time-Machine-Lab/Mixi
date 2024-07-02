package com.mixi.server.netty.codec;

import com.mixi.server.netty.protocol.AccessMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/24 9:59
 */
@ChannelHandler.Sharable
public class ServerMessageWebSocketEncoder extends MessageToMessageEncoder<AccessMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AccessMessage msg, List<Object> list) {
        ByteBuf buf = null;
        try {
            buf = ctx.alloc().ioBuffer();
            MessageCodec.encode(buf, msg);
            WebSocketFrame frame = new BinaryWebSocketFrame(buf);
            list.add(frame);
            buf = null;
        } finally {
            if (buf != null) {
                buf.release();
            }
        }
    }
}
