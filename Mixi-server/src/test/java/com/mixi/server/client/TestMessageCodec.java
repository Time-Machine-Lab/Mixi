package com.mixi.server.client;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mixi.server.netty.NettyServerHandler;
import com.mixi.server.netty.codec.MessageCodec;
import com.mixi.server.netty.codec.ServerMessageWebSocketDecoder;
import com.mixi.server.netty.codec.ServerMessageWebSocketEncoder;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.protocol.AccessResponse;
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
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/27 14:16
 */
@SpringBootTest
public class TestMessageCodec {
    private static SocketChannel channel;
    private static String uri;
    public static void start() {
        try {
            WebSocketClient client = new WebSocketClient(new URI(uri)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected");

                    // Create the message
                    byte[] message = AccessMessageEncoder.createWebSocketMessage();

                    // Send the message
                    ByteBuffer buffer = ByteBuffer.wrap(message);
                    send(buffer);
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                }

                @Override
                public void onMessage(ByteBuffer message) {
                    System.out.println("Received ByteBuffer message: "+message);
                    ByteBuf byteBuf = Unpooled.wrappedBuffer(message);
                    AccessMessage decode = MessageCodec.decode(byteBuf);
                    String s = new String(decode.getBody(), StandardCharsets.UTF_8);
                    System.out.println(s);
                    Object o = JSONObject.parseObject(s, AccessResponse.class);
                    System.out.println(o);
                    System.out.println(decode);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            WebSocketClient client1 = new WebSocketClient(new URI(uri)){
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected");
                    // Create the message
                    byte[] message = AccessMessageEncoder.createWebSocketMessage();

                    // Send the message
                    ByteBuffer buffer = ByteBuffer.wrap(message);
                    send(buffer);
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                }

                @Override
                public void onMessage(ByteBuffer message) {
                    System.out.println("Received ByteBuffer message: "+message);
                    ByteBuf byteBuf = Unpooled.wrappedBuffer(message);
                    AccessMessage decode = MessageCodec.decode(byteBuf);
                    String s = new String(decode.getBody(), StandardCharsets.UTF_8);
                    System.out.println(s);
                    Object o = JSONObject.parseObject(s, AccessResponse.class);
                    System.out.println(o);
                    System.out.println(decode);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        uri = "ws://localhost:8090/chat";
        start();
    }
}
