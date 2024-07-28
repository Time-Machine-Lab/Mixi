package com.mixi.server.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mixi.server.netty.NettyServerHandler;
import com.mixi.server.netty.codec.MessageCodec;
import com.mixi.server.netty.codec.ServerMessageWebSocketDecoder;
import com.mixi.server.netty.codec.ServerMessageWebSocketEncoder;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.protocol.AccessResponse;
import com.mixi.server.netty.protocol.ChatroomMsg;
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
    private static String uri;
    public static void start() {
        try {
            WebSocketClient client = new WebSocketClient(new URI(uri)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("client"+"Connected");

                    // Create the message
                    byte[] message = AccessMessageEncoder.roomMessage(123,10,"");

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

                    ByteBuf byteBuf = Unpooled.wrappedBuffer(message);
                    AccessMessage decode = MessageCodec.decode(byteBuf);
                    String s = new String(decode.getBody(), StandardCharsets.UTF_8);
                    System.out.println("client "+"Received ByteBuffer message: "+decode+s);
                    System.out.println(s);
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
                    System.out.println("client1"+"Connected");
                    // Create the message
                    byte[] message = AccessMessageEncoder.roomMessage(1234,10,"");

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

                    ByteBuf byteBuf = Unpooled.wrappedBuffer(message);
                    AccessMessage decode = MessageCodec.decode(byteBuf);
                    String body = new String(decode.getBody(),StandardCharsets.UTF_8);
                    System.out.println("client1 "+"Received ByteBuffer message: "+decode);
                    System.out.println(body);
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
            Thread.sleep(10000);
            client1.connect();
            byte[] message = AccessMessageEncoder.roomMessage(123,12,"我觉得柳州螺蛳粉大于株洲螺蛳粉");
            // Send the message
            ByteBuffer buffer = ByteBuffer.wrap(message);
            Thread.sleep(1000);
            client.send(buffer);
            Thread.sleep(500);
            byte[] message1 = AccessMessageEncoder.roomMessage(1234,12,"我同意");
            ByteBuffer buffer1 = ByteBuffer.wrap(message1);
            client1.send(buffer1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        uri = "ws://localhost:8090/chat";
        start();
    }
}
