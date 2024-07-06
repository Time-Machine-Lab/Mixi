package com.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述: 黑名单与白名单
 * @author suifeng
 * 日期: 2024/7/5
 */
@ConfigurationProperties(prefix = "mx.auth")
@Component
@Data
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;
}
