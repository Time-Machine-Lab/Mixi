package com.mixi.server.netty.channel;

import com.mixi.server.netty.channel.support.ChannelAttrs;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/1 1:26
 */
@Data
public class MixiNettyChannel implements MixiChannelManager{

    private static final Logger log = LoggerFactory.getLogger(MixiNettyChannel.class);
    private static final ConcurrentHashMap<String, MixiNettyChannel> CHANNEL_MAP = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Object> attributes = new ConcurrentHashMap<>();
    private Channel channel;
    private boolean close;
    private volatile boolean sleep;

    public MixiNettyChannel(Channel channel){
        this.channel = channel;
    }

    public static MixiNettyChannel registerChannelIfAbsent(@NonNull Channel ch) {
        String channelId = ChannelAttrs.getChannelId(ch);
        MixiNettyChannel nettyChannel = CHANNEL_MAP.get(channelId);
        if(nettyChannel==null){
            //TODO:后续考虑并发问题
            MixiNettyChannel newChannel = new MixiNettyChannel(ch);
            if(ch.isActive()){
                CHANNEL_MAP.putIfAbsent(channelId,newChannel);
            }
            nettyChannel=newChannel;
        }
        return nettyChannel;
    }

    public static void removeChannel(@NonNull Channel channel) {
        MixiNettyChannel nettyChannel = CHANNEL_MAP.remove(ChannelAttrs.getChannelId(channel));
    }

    public static Collection<MixiNettyChannel> getAllChannels() {
        return CHANNEL_MAP.values();
    }

    public static MixiNettyChannel getChannelById(String uid){
        return CHANNEL_MAP.get(uid);
    }

    public String getChannelId() {
        return ChannelAttrs.getAttrs(channel).getChannelId();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) channel.localAddress();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }

    @Override
    public boolean isConnected() {
        return channel.isActive()&&!close;
    }

    @Override
    public void send(Object message){
        if(!this.isConnected()){
            log.error("Netty channel is close :[channelId:"+getChannelId()+"]");
        }
        channel.writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public void close() {
        channel.close();
        this.close = true;
    }

    @Override
    public boolean isClosed() {
        return this.close;
    }

    @Override
    public boolean hasAttribute(String key) {
        return false;
    }

    public <T> T getAttribute(String key, Class<T> type) {
        return type.cast(getAttribute(key));
    }

    @Override
    public Object getAttribute(String key) {
        return null;
    }

    @Override
    public void setAttribute(String key, Object value) {

    }
    public <T> T removeAttribute(String key, Class<T> type) {
        return type.cast(removeAttribute(key));
    }
    @Override
    public Object removeAttribute(String key) {
        return null;
    }
    public ChannelAttrs getAttrsIfExists() {
        return ChannelAttrs.getAttrsIfExists(channel);
    }
    public ChannelAttrs getAttrs() {
        return ChannelAttrs.getAttrs(channel);
    }
    @Override
    public final int hashCode() {
        return channel.id().hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MixiNettyChannel)) {
            return false;
        }
        return ((MixiNettyChannel) o).channel.id().asLongText().equals(this.channel.id().asLongText());
    }
}
