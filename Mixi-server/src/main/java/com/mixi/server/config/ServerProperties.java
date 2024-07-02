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
public class ServerProperties {

    private Integer maxPipelineNum=2048;
    private Integer wsPort=8090;

    public Integer getMaxPipelineNum() {
        return maxPipelineNum;
    }

    public void setMaxPipelineNum(Integer maxPipelineNum) {
        this.maxPipelineNum = maxPipelineNum;
    }

    public Integer getWsPort() {
        return wsPort;
    }

    public void setWsPort(Integer wsPort) {
        this.wsPort = wsPort;
    }
}
