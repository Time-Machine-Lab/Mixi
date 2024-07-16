package com.infrastructure.core.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private volatile Map<String, ApiInfo> currentApiInfos = new HashMap<>();

    // 读写锁，确保缓存读写操作的线程安全性
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 刷新配置文件
     */
    public void reFreshConfig(String configInfo) {
        try {
            updateConfigInternal(configInfo);
        } catch (Exception e) {
            log.error("Error update new config: ", e);
        }
    }

    /**
     * 更新配置的内部方法
     */
    private void updateConfigInternal(String configInfo) {
        Map<String, ApiInfo> newApiInfos = parseJsonConfigToMap(configInfo);
        switchCache(newApiInfos);
    }

    /**
     * 切换缓存
     */
    private void switchCache(Map<String, ApiInfo> newApiInfos) {
        try {
            lock.writeLock().lock();
            currentApiInfos = newApiInfos;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 获取当前生效的配置
     */
    public Map<String, ApiInfo> getConfig() {
        try {
            lock.readLock().lock();
            return new HashMap<>(currentApiInfos);
        } finally {
            lock.readLock().unlock();
        }
    }

    private Map<String, ApiInfo> parseJsonConfigToMap(String config) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<ApiInfo>> stringListMap = config == null ? new HashMap<>() : objectMapper.readValue(config, new TypeReference<Map<String, List<ApiInfo>>>() {});
            Map<String, ApiInfo> apiInfos = new HashMap<>();
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