package com.mixiserver.netty;

import io.netty.channel.ChannelDuplexHandler;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/24 10:11
 */
//复用管道处理器，做统一处理
@io.netty.channel.ChannelHandler.Sharable
public class NettyServerHandler extends ChannelDuplexHandler {

}
