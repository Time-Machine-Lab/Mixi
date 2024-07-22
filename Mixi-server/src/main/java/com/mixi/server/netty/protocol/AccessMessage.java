package com.mixi.server.netty.protocol;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/24 13:27
 */
@Data
public class AccessMessage {

    private int version;
    private boolean heartBeat;
    private int cmd;
    private int length;
    private byte[] body;
    private List<Header> headers;
}
