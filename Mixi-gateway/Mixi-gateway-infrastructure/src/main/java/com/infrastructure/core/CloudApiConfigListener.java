package com.infrastructure.core;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.mixi.common.config.CloudApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 描述: 云接口配置文件监听器，负责监听配置文件的变化并触发配置更新
 * @author suifeng
 * 日期: 2024/7/7
 */
@RequiredArgsConstructor
@SuppressWarnings("all")
@Component
@Slf4j
public class CloudApiConfigListener {

    private final GateWayManager gateWayManager;
    private final CloudApiProperties cloudApiProperties;

    /**
     * 添加配置监听器
     */
    public void addListener(ConfigService configService, String nacosDataId) throws NacosException {
        configService.addListener(nacosDataId, cloudApiProperties.getGroup(), new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("Received new configuration: {}", nacosDataId);

                // 更新配置文件
                gateWayManager.updateConfig(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }
        });
    }
}
