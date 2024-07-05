package com.mixi.server.netty.channel.support;

import com.mixi.server.util.NetUtils;

import java.net.InetSocketAddress;
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
        StringBuilder sb = new StringBuilder(96);
        sb.append(NetUtils.LOCAL_IP_ADDR);
        sb.append(CH_ID_SEPARATOR);
        sb.append(remoteAddr.getAddress().getHostAddress());
        sb.append(CH_ID_SEPARATOR);
        sb.append(remoteAddr.getPort());
        sb.append(CH_ID_SEPARATOR);
        sb.append(timestamp);
        sb.append(CH_ID_SEPARATOR);
        sb.append(Integer.toHexString(SEQ.getAndIncrement()));
        return sb.toString();
    }
}
