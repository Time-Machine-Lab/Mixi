package com.infrastructure.core.init;

import com.alibaba.nacos.api.config.ConfigService;
import com.infrastructure.core.manager.GateWayManager;
import com.mixi.common.config.CloudApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static com.mixi.common.config.CloudApiProperties.createConfigService;

/**
 * 描述: 网关初始化器，负责初始化网关模块的配置并添加配置监听器。
 * @author suifeng
 * 日期: 2024/7/11
 */
@RequiredArgsConstructor
@SuppressWarnings("all")
@Component
@Slf4j
public class GatewayInitializer implements InitializingBean {

    private final CloudApiConfigListener cloudApiConfigListener;
    private final CloudApiProperties cloudApiProperties;
    private final GateWayManager gatewayManager;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // 网关所支持的模块，supportModules: user,admin,webroom
    @Value("${spring.cloud.gateway.supportModules:}")
    private String supportModules;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 区分出不同的配置文件环境
            String nacosDataId = String.format(cloudApiProperties.getDataIdPattern(), activeProfile);

            // 构建Nacos配置请求服务
            ConfigService configService = createConfigService(cloudApiProperties);

            // 从Nacos拉取现有的接口配置文件
            String existConfig = configService.getConfig(nacosDataId, cloudApiProperties.getGroup(), 5000);

            // 初始化配置
            gatewayManager.initSupportModules(supportModules);
            gatewayManager.initConfig(existConfig);
            log.info("Initial config: {}", existConfig);

            // 添加监听器
            cloudApiConfigListener.addListener(configService, nacosDataId);
        } catch (Exception e) {
            log.error("Error init GatewayInitializer: ", e);
        }
    }
}
