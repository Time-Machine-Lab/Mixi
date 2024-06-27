package com.mixi.server;

import com.mixi.server.config.ServerProperties;
import com.mixi.server.netty.NettyFactory;
import com.mixi.server.netty.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/23 15:57
 */
@Component
public class NettyServerBoot implements ApplicationListener<ContextClosedEvent> {

    private Logger log = LoggerFactory.getLogger(NettyServerBoot.class);
    @Resource
    ServerProperties properties;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channelWs;
    public void start(){
        ChannelFuture cfWs;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bossGroup = NettyFactory.eventLoopGroup(1, "bossLoopGroup");
            workerGroup = NettyFactory.eventLoopGroup(4, "workerLoopGroup");
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NettyFactory.serverSocketChannelClass())
                    .option(ChannelOption.SO_BACKLOG,properties.getMaxPipelineNum())
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,new WriteBufferWaterMark(64*1024,128 * 1024))
                    .childHandler(new NettyServerInitializer());
            cfWs = bootstrap.bind(properties.getWsPort()).sync();
            channelWs = cfWs.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("Shutdown Netty Server...");
        try {
            channelWs.close();
        }catch (Throwable e){
            log.error("websocket channel close failed !",e);
        }
    }
}
