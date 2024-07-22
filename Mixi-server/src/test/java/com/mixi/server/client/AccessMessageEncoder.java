package com.mixi.server.client;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AccessMessageEncoder {

    public static ByteBuffer encodeVarInt(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(5);
        while ((value & 0xFFFFFF80) != 0L) {
            buffer.put((byte) ((value & 0x7F) | 0x80));
            value >>>= 7;
        }
        buffer.put((byte) (value & 0x7F));
        buffer.flip();
        return buffer;
    }

    public static byte[] stringToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] createWebSocketMessage() {
        byte version = 1;
        boolean heartBeat = false;
        byte cmd = 1; // 加入房间

        List<Header> headers = new ArrayList<>();
        headers.add(new Header(2, stringToBytes("{\n" +
                "    \"room\": 123,\n" +
                "    \"uid\": 123,\n" +
                "    \"cmd\": 10\n" +
                "}")));

        byte[] body = stringToBytes("this is the body");

        int totalLength = 0;
        for (Header header : headers) {
            totalLength += encodeVarInt(header.data.length).remaining() + 1 + header.data.length;
        }
        totalLength += body.length;

        int bufferSize = 1 + 1 + 1 + encodeVarInt(totalLength).remaining() + 1;
        for (Header header : headers) {
            bufferSize += encodeVarInt(header.data.length).remaining() + 1 + header.data.length;
        }
        bufferSize += body.length;

        ByteBuffer buf = ByteBuffer.allocate(bufferSize);

        buf.put(version);

        buf.put((byte) (heartBeat ? 1 : 0));

        if (heartBeat) {
            buf.flip();
            byte[] byteArray = new byte[buf.remaining()];
            buf.get(byteArray);
            return byteArray;
        }

        buf.put(cmd);

        buf.put(encodeVarInt(totalLength));

        buf.put((byte) headers.size());

        for (Header header : headers) {
            buf.put(encodeVarInt(header.data.length));
            buf.put((byte) header.type);
            buf.put(header.data);
        }

        buf.put(body);

        buf.flip();
        byte[] byteArray = new byte[buf.remaining()];
        buf.get(byteArray);
        return byteArray;
    }

    public static void main(String[] args) {
        byte[] message = createWebSocketMessage();
        for (byte b : message) {
            System.out.printf("%02x ", b);
        }
    }

    static class Header {
        int type;
        byte[] data;

        Header(int type, byte[] data) {
            this.type = type;
            this.data = data;
        }
    }
}
