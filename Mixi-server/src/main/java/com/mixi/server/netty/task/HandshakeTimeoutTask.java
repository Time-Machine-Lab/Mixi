package com.mixi.server.netty.task;

import com.mixi.server.netty.channel.MixiNettyChannel;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 20:29
 */
@Slf4j
public class HandshakeTimeoutTask implements TimerTask {

    private final MixiNettyChannel channel;

    public HandshakeTimeoutTask(MixiNettyChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run(Timeout timeout) {
        try {
            if (!channel.isConnected()) {
                return;
            }
            channel.close();
            log.info("HandshakeNotReceived, remoteAddress={}", channel.getRemoteAddress());
        } catch (Throwable t) {
            log.warn("Exception when handle handshake task , channel " + channel.getRemoteAddress(), t);
        }
    }
}
