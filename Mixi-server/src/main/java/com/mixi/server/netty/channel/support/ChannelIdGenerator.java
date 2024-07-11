package com.mixi.server.netty.channel.support;

import com.mixi.server.util.NetUtils;

import java.net.InetSocketAddress;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/3 14:24
 */

public class ChannelIdGenerator {
    private static final String CH_ID_SEPARATOR = "|";

    private static final AtomicInteger SEQ = new AtomicInteger();

    private ChannelIdGenerator() {
    }

    public static String generateChannelId(InetSocketAddress remoteAddr, long timestamp) {
        return new StringJoiner(CH_ID_SEPARATOR)
                .add(NetUtils.LOCAL_IP_ADDR)
                .add(remoteAddr.getAddress().getHostAddress())
                .add(String.valueOf(remoteAddr.getPort()))
                .add(String.valueOf(timestamp))
                .add(Integer.toHexString(SEQ.getAndIncrement()))
                .toString();
    }
}
