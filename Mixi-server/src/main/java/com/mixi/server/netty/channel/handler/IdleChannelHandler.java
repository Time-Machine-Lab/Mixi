package com.mixi.server.netty.channel.handler;

import cn.hutool.core.thread.NamedThreadFactory;
import com.mixi.server.config.ServerProperties;
import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.task.HandshakeTimeoutTask;
import com.mixi.server.netty.task.HeartbeatTimerTask;
import com.mixi.server.util.AccessMessageUtils;
import com.mixi.server.util.ApplicationContextUtils;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import lombok.NonNull;

import javax.annotation.Resource;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/5 19:51
 */
public class IdleChannelHandler extends AbstractChannelHandler{
    public static final HashedWheelTimer IDLE_CHECK_TIMER = new HashedWheelTimer(new NamedThreadFactory("mercury-idleCheck", true), 1, TimeUnit.SECONDS, 128);
    public static final String KEY_READ_TIMESTAMP = "READ_TIMESTAMP";
    public static final String KEY_WRITE_TIMESTAMP = "WRITE_TIMESTAMP";
    public static final String KEY_HAND_SHAKE = "HAND_SHAKE_TIMER";

    protected IdleChannelHandler(@NonNull ChannelHandler handler) {
        super(handler);
        startIdleTask();
    }

    private static void startIdleTask(){
        ServerProperties properties = ApplicationContextUtils.getBean(ServerProperties.class);
        //心跳检测
        int channelIdleSeconds = properties.getChannelIdleSeconds();
        HeartbeatTimerTask heartbeatTimerTask = new HeartbeatTimerTask(30 * 1000, channelIdleSeconds * 1000);
        IDLE_CHECK_TIMER.newTimeout(heartbeatTimerTask, 10, TimeUnit.SECONDS);
    }

    @Override
    public void connect(MixiNettyChannel channel) {
        setReadTimestamp(channel);
        setWriteTimestamp(channel);
        handler.connect(channel);
        startHandshakeTask(channel);
    }

    private static void startHandshakeTask(MixiNettyChannel channel) {
        ServerProperties properties = ApplicationContextUtils.getBean(ServerProperties.class);
        //握手认证
        int handshakeWaitSeconds = properties.getHandshakeWaitSeconds();
        if (handshakeWaitSeconds > 0) {
            HandshakeTimeoutTask handshakeTimeoutTask = new HandshakeTimeoutTask(channel);
            channel.setAttribute(KEY_HAND_SHAKE, IDLE_CHECK_TIMER.newTimeout(handshakeTimeoutTask, handshakeWaitSeconds, TimeUnit.SECONDS));
        }
    }

    private void setWriteTimestamp(MixiNettyChannel channel) {
        channel.setAttribute(KEY_WRITE_TIMESTAMP, System.currentTimeMillis());
    }

    private void setReadTimestamp(MixiNettyChannel channel) {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
    }

    @Override
    public void disconnect(MixiNettyChannel channel) {
        clearReadTimestamp(channel);
        clearWriteTimestamp(channel);
        handler.disconnect(channel);
        closeTask(channel);
    }

    private void closeTask(MixiNettyChannel channel) {
        Timeout timeout = channel.removeAttribute(KEY_HAND_SHAKE, Timeout.class);
        if (timeout != null) {
            timeout.cancel();
        }
    }

    private void clearWriteTimestamp(MixiNettyChannel channel) {
        channel.removeAttribute(KEY_WRITE_TIMESTAMP);
    }

    private void clearReadTimestamp(MixiNettyChannel channel) {
        channel.removeAttribute(KEY_READ_TIMESTAMP);
    }

    @Override
    public void send(MixiNettyChannel channel, Object message) {
        setWriteTimestamp(channel);
        super.send(channel, message);
    }

    @Override
    public void receive(MixiNettyChannel channel, Object message) {
        setReadTimestamp(channel);
        AccessMessage msg = (AccessMessage) message;
        if (!msg.isHeartBeat()) {
            handler.receive(channel, message);
            return;
        }
        channel.send(AccessMessageUtils.createHeartResponse());
    }
}
