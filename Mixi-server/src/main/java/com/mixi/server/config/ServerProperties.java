package com.mixi.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/23 15:59
 */
@Configuration
@ConfigurationProperties("mixi.netty")
@Data
public class ServerProperties {

    private Integer maxPipelineNum=2048;
    private Integer wsPort=8090;
    private Integer channelIdleSeconds=180;
    private Integer probeIdleSeconds=120;
    private Integer probeWaitSeconds=5;
    private Integer handshakeWaitSeconds=5;
}
