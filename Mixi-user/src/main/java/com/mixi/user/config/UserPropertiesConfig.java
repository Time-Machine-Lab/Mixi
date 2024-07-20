package com.mixi.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mixi")
public class UserPropertiesConfig {

    private String verifyLinkUrl;
}
