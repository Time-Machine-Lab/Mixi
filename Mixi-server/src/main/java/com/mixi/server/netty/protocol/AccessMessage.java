package com.mixi.server.netty.protocol;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/24 13:27
 */
public class AccessMessage {

    private int version;
    private boolean heartBeat;
    private int cmd;
    private int length;
    private byte[] body;
    private List<Header> headers;

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(boolean heartBeat) {
        this.heartBeat = heartBeat;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AccessMessage{" +
                "version=" + version +
                ", cmd=" + cmd +
                ", length=" + length +
                ", headers=" + headers +
                ", body=" + (body == null ? null : new String(body, StandardCharsets.UTF_8)) +
                '}';
    }
}
