package com.mixi.server.netty.task;

import com.mixi.server.netty.channel.MixiNettyChannel;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 20:06
 */
public abstract class AbstractTimerTask implements TimerTask {

    private final long tick;

    protected AbstractTimerTask(long tick) {
        this.tick = tick;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        Collection<MixiNettyChannel> allChannels = MixiNettyChannel.getAllChannels();
        for (MixiNettyChannel channel : allChannels) {
            if (channel.isConnected()) {
                doTask(channel);
            }
        }
        reput(timeout,tick,this);
    }

    protected abstract void doTask(MixiNettyChannel channel);

    protected void reput(Timeout timeout, long tick, TimerTask task) {
        Timer timer = timeout.timer();
        if (timer == null) {
            return;
        }
        timer.newTimeout(task, tick, TimeUnit.MILLISECONDS);
    }
}
