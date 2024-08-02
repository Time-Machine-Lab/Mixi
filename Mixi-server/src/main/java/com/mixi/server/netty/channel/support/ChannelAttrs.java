package com.mixi.server.netty.channel.support;

import cn.hutool.core.collection.ConcurrentHashSet;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.Set;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/3 14:17
 */
@Data
public class ChannelAttrs {

    public static final AttributeKey<ChannelAttrs> MIXI_ATTRS = AttributeKey.valueOf("Mixi.ATTRS");
    private final long createTime;
    private final String channelId;
    private int channelResource = -1;
    private String deviceId;
    private String uid;
    private boolean isVisitor = false;
    private boolean isEnter = false;
    private Set<String> rooms = new ConcurrentHashSet<>(2);

    public ChannelAttrs(InetSocketAddress remoteAddr){
        this.createTime = System.currentTimeMillis();
        this.channelId = ChannelIdGenerator.generateChannelId(remoteAddr, createTime);
    }
    public static ChannelAttrs init(Channel ch, InetSocketAddress remoteAddr) {
        ChannelAttrs attrs = new ChannelAttrs(remoteAddr);
        attrs = ch.attr(MIXI_ATTRS).setIfAbsent(attrs);
        return attrs;
    }

    public static String getChannelId(Channel ch){
        ChannelAttrs attrs = getAttrs(ch);
        return attrs==null?null:attrs.getChannelId();
    }

    public static ChannelAttrs getAttrs(Channel ch){
        return ch.attr(MIXI_ATTRS).get();
    }
    public static ChannelAttrs getAttrsIfExists(io.netty.channel.Channel ch) {
        return ch.attr(MIXI_ATTRS).get();
    }
}
