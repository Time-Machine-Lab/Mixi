package com.mixiserver.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/23 15:59
 */
@Configuration
@Data
public class ServerProperties {

    private Integer maxPipelineNum;
    private Integer wsPort;

}
