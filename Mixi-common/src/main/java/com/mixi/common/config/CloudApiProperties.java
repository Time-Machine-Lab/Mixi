package com.mixi.common.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@ConfigurationProperties(prefix = "spring.cloud.nacos")
@Configuration
@Slf4j
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
        try {
            Properties properties = new Properties();
            properties.put("serverAddr", cloudApiProperties.getServerAddr());
            properties.put("username", cloudApiProperties.getUsername());
            properties.put("password", cloudApiProperties.getPassword());
            properties.put("namespace", cloudApiProperties.getNamespace());
            return NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
           log.error("Please check that the configuration in the yaml configuration file is correct");
           throw new Exception(e.getMessage());
        }
    }
}