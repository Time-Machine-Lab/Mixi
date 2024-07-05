package com.mixi.server.netty.channel.support;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;
import java.time.LocalDate;

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
        String chId = attrs.getChannelId();
        if(StringUtils.isBlank(chId)){
            throw new IllegalStateException("ChannelId is blank! ch="+ch);
        }
        return chId;
    }

    public static ChannelAttrs getAttrs(Channel ch){
        ChannelAttrs channelAttrs = ch.attr(MIXI_ATTRS).get();
        Assert.notNull(channelAttrs,"MIXI_ATTRS is null! ch="+ch);
        return channelAttrs;
    }
}
