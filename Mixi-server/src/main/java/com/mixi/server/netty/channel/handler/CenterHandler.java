package com.mixi.server.netty.channel.handler;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.handler.ChannelHandler;
import com.mixi.server.netty.handler.MixiHandler;
import com.mixi.server.netty.handler.MixiHandlerRouter;
import com.mixi.server.netty.protocol.AccessMessage;
import com.mixi.server.netty.protocol.HeaderEnum;
import com.mixi.server.util.AccessMessageUtils;
import org.springframework.util.Assert;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:48
 */
public class CenterHandler implements ChannelHandler {

    @Override
    public void connect(MixiNettyChannel channel) {

    }

    @Override
    public void disconnect(MixiNettyChannel channel) {

    }

    @Override
    public void send(MixiNettyChannel channel, Object msg) {

    }

    @Override
    public void receive(MixiNettyChannel channel, Object msg) {
        AccessMessage message = (AccessMessage) msg;
        MixiHandler handler = MixiHandlerRouter.route(message.getCmd());
        Assert.notNull(handler,"cmd is error! please check the cmd");
        handler.processEvent(channel,message);
    }

    @Override
    public void caught(MixiNettyChannel channel, Throwable throwable) {

    }


}
