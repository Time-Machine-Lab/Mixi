package com.component.core;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixi.common.config.CloudApiProperties;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.mixi.common.config.CloudApiProperties.createConfigService;

/**
 * 描述: 云接口配置文件管理器，启动网关时拉取一次配置并初始化，
 *       并且添加一个监听器，之后云端配置发布之后会自动同步
 * @author suifeng
 * 日期: 2024/7/7
 */
@RequiredArgsConstructor
@SuppressWarnings("all")
@Component
@Slf4j
public class CloudApiConfigManager implements InitializingBean {

    private final CloudApiProperties cloudApiProperties;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // 用于缓存当前云端的接口配置信息
    private final CopyOnWriteArrayList<ApiInfo> apiInfos = new CopyOnWriteArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        // 区分出不同的配置文件环境
        String nacosDataId = String.format(cloudApiProperties.getDataIdPattern(), activeProfile);

        // 构建Nacos配置请求服务
        ConfigService configService = createConfigService(cloudApiProperties);

        // 从Nacos拉取现有的接口配置文件
        String existConfig = configService.getConfig(nacosDataId, cloudApiProperties.getGroup(), 5000);

        handleNewConfig(existConfig);
        log.info("Initial config: {}", existConfig);

        // 添加监听器
        configService.addListener(nacosDataId, cloudApiProperties.getGroup(), new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("Received new configuration: {}", nacosDataId);
                // 处理新的配置
                handleNewConfig(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }
        });
    }

    /**
     *  异步处理更新逻辑
     */
    @Async
    private void handleNewConfig(String configInfo) {
        // 更新缓存
        updateConfigCache(configInfo);

        // 更新网关本地配置
        log.info("Updating gateway configuration with new config: {}", configInfo);
    }

    /**
     *  更新本地云接口配置文件
     */
    private void updateConfigCache(String configInfo) {
        try {
            // 清理本地配置缓存
            apiInfos.clear();

            // 更新本地配置缓存
            Map<String, List<ApiInfo>> stringListMap = parseJsonConfigToMap(configInfo);
            for (Map.Entry<String, List<ApiInfo>> stringListEntry : stringListMap.entrySet()) {
                apiInfos.addAll(stringListEntry.getValue());
            }
        } catch (Exception e) {
            log.error("Error updating config cache: ", e);
        }
    }

    /**
     *  解析原始的JSON配置文件
     */
    private Map<String, List<ApiInfo>> parseJsonConfigToMap(String config) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return config == null ? new HashMap<>() : objectMapper.readValue(config, Map.class);
    }

    /**
     *  获取云接口信息集合
     */
    public List<ApiInfo> getConfig() {
        return new ArrayList<>(apiInfos);
    }
}
