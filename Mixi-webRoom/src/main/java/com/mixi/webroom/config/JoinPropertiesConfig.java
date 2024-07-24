package com.mixi.webroom.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author XiaoChun
 * @date 2024/7/23
 */
@Data
@Component
@ConfigurationProperties(prefix = "mixi")
public class JoinPropertiesConfig {
    private String verifyLinkUrl;
}
