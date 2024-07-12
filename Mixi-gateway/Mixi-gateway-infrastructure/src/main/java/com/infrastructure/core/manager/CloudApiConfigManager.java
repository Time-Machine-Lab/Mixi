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

    // 临时缓存，用于存储新拉取的配置
    private Map<String, ApiInfo> tempApiInfos = new HashMap<>();

    // 读写锁，确保缓存读写操作的线程安全性
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 初始化配置文件
     */
    public void initConfig(String configInfo) {
        try {
            updateTempCache(configInfo);
            switchCache();
        } catch (Exception e) {
            log.error("Error init config: ", e);
        }
    }

    /**
     * 异步处理新的配置
     */
    @Async
    public void updateConfig(String configInfo) {
        try {
            updateTempCache(configInfo);
            switchCache();
        } catch (Exception e) {
            log.error("Error update new config: ", e);
        }
    }

    /**
     * 更新临时缓存
     */
    private void updateTempCache(String configInfo) {
        try {
            lock.writeLock().lock();
            tempApiInfos.clear();
            Map<String, List<ApiInfo>> stringListMap = parseJsonConfigToMap(configInfo);
            for (Map.Entry<String, List<ApiInfo>> stringListEntry : stringListMap.entrySet()) {
                for (ApiInfo apiInfo : stringListEntry.getValue()) {
                    tempApiInfos.put(apiInfo.generateHashKey(), apiInfo);
                }
            }
        } catch (Exception e) {
            log.error("Error update temp cache: ", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 切换缓存
     */
    private void switchCache() {
        try {
            lock.writeLock().lock();
            currentApiInfos = new HashMap<>(tempApiInfos);
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

    private Map<String, List<ApiInfo>> parseJsonConfigToMap(String config) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return config == null ? new HashMap<>() : objectMapper.readValue(config, new TypeReference<Map<String, List<ApiInfo>>>() {});
    }
}