package com.mixi.common.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@ConfigurationProperties(prefix = "spring.cloud.nacos")
@Configuration
@Data
public class CloudApiProperties {

    private String serverAddr;

    private String username;

    private String password;

    private String namespace;

    private String group = "DEFAULT_GROUP";

    private String dataIdPattern = "cloud-api-%s.json";

    /**
     * 构建配置请求服务
     */
    public static ConfigService createConfigService(CloudApiProperties cloudApiProperties) throws Exception {
        Properties properties = new Properties();
        properties.put("serverAddr", cloudApiProperties.getServerAddr());
        properties.put("username", cloudApiProperties.getUsername());
        properties.put("password", cloudApiProperties.getPassword());
        properties.put("namespace", cloudApiProperties.getNamespace());
        return NacosFactory.createConfigService(properties);
    }
}