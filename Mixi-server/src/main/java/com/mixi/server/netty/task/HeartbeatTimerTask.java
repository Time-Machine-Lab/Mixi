package com.mixi.server.netty.task;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.handler.IdleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 20:04
 */
public class HeartbeatTimerTask extends AbstractTimerTask{

    private final int timeout;
    private static Logger log = LoggerFactory.getLogger(HeartbeatTimerTask.class);
    public HeartbeatTimerTask(long tick,int timeout) {
        super(tick);
        this.timeout=timeout;
    }

    @Override
    protected void doTask(MixiNettyChannel channel) {
        try {
            if (!channel.isConnected()) {
                return;
            }
            long now = System.currentTimeMillis();
            boolean isReadTimeout = isReadTimeout(channel, now);
            boolean isWriteTimeout = isWriteTimeout(channel, now);
            if (isReadTimeout || isWriteTimeout) {
                channel.close();
            }
        } catch (Throwable t) {
            log.error("Exception when close channel " + channel.getRemoteAddress(), t);
        }
    }
    protected boolean isReadTimeout(MixiNettyChannel channel, long now) {
        Long lastRead = lastRead(channel);
        return lastRead != null && now - lastRead > timeout;
    }

    protected boolean isWriteTimeout(MixiNettyChannel channel, long now) {
        Long lastWrite = lastWrite(channel);
        return lastWrite != null && now - lastWrite > timeout;
    }

    public static Long lastRead(MixiNettyChannel channel) {
        return channel.getAttribute(IdleChannelHandler.KEY_READ_TIMESTAMP, Long.class);
    }

    public static Long lastWrite(MixiNettyChannel channel) {
        return channel.getAttribute(IdleChannelHandler.KEY_WRITE_TIMESTAMP, Long.class);
    }
}
