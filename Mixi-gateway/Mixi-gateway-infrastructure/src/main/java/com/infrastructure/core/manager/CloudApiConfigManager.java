package com.infrastructure.core.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 描述: 云接口配置文件管理器，用于操作API配置文件
 * @author suifeng
 * 日期: 2024/7/10
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class CloudApiConfigManager {

    // 当前生效的配置缓存
    private final AtomicReference<Map<String, ApiInfo>> currentApiInfos = new AtomicReference<>(new ConcurrentHashMap<>());

    /**
     * 刷新配置文件
     */
    public void reFreshConfig(String configInfo) {
        try {
            Map<String, ApiInfo> newApiInfos = parseJsonConfigToMap(configInfo);
            currentApiInfos.set(newApiInfos);
        } catch (Exception e) {
            log.error("Error update new config: ", e);
        }
    }

    /**
     * 获取当前生效的配置
     */
    public Map<String, ApiInfo> getConfig() {
        return currentApiInfos.get();
    }

    private Map<String, ApiInfo> parseJsonConfigToMap(String config) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<ApiInfo>> stringListMap = config == null ? new HashMap<>() : objectMapper.readValue(config, new TypeReference<Map<String, List<ApiInfo>>>() {});
            Map<String, ApiInfo> apiInfos = new ConcurrentHashMap<>();
            for (Map.Entry<String, List<ApiInfo>> entry : stringListMap.entrySet()) {
                for (ApiInfo apiInfo : entry.getValue()) {
                    apiInfos.put(apiInfo.generateHashKey(), apiInfo);
                }
            }
            return apiInfos;
        } catch (Exception e) {
            log.error("Error parsing JSON config: ", e);
            return new HashMap<>();
        }
    }
}