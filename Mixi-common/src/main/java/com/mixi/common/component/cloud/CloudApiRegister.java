package com.mixi.common.component.cloud;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mixi.common.config.CloudApiProperties;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.mixi.common.config.CloudApiProperties.createConfigService;

/**
 * 描述: 云接口注册器，负责将收集器收集的接口信息同步到云端
 * @author suifeng
 * 日期: 2024/7/6
 */
// 在收集器之后执行注册逻辑
@DependsOn("cloudApiCollector")
@SuppressWarnings("all")
@RequiredArgsConstructor
@Component
@Slf4j
public class CloudApiRegister implements InitializingBean {

    private final CloudApiCollector cloudApiCollector;
    private final CloudApiProperties cloudApiProperties;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void afterPropertiesSet() throws Exception {

        // 拉取收集到的接口信息
        List<ApiInfo> apiInfoList = cloudApiCollector.getApiInfoList();

        // 收集不到接口信息，就不用同步到云端了
        if (apiInfoList == null || apiInfoList.isEmpty()) {
            log.warn("No API info collected, skipping registration.");
            return;
        }

        // 区分出不同的配置文件环境
        String nacosDataId = String.format(cloudApiProperties.getDataIdPattern(), activeProfile);

        // 构建Nacos配置请求服务
        ConfigService configService = createConfigService(cloudApiProperties);

        // 从Nacos拉取现有的接口配置文件
        String existConfig = configService.getConfig(nacosDataId, cloudApiProperties.getGroup(), 5000);

        // 解析Json配置文件
        Map<String, Object> jsonMap = parseJsonConfig(existConfig);

        // 构建新的配置文件
        String newConfig = buildApiConfig(jsonMap, apiInfoList);

        // 重新发布，更新云端配置文件
        configService.publishConfig(nacosDataId, cloudApiProperties.getGroup(), newConfig, ConfigType.JSON.getType());
    }

    private Map<String, Object> parseJsonConfig(String config) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return config == null ? new HashMap<>() : objectMapper.readValue(config, Map.class);
    }

    private String buildApiConfig(Map<String, Object> jsonMap, List<ApiInfo> apiInfoList) throws Exception {

        // 获取当前模块名
        String currentModule = apiInfoList.get(0).getModule();

        // 获取现有的API信息
        Map<String, Map<String, List<ApiInfo>>> moduleApiMap =
                Optional.ofNullable((Map<String, Map<String, List<ApiInfo>>>) jsonMap.get("api_list")).orElse(new HashMap<>());

        // 删除旧的模块配置
        moduleApiMap.remove(currentModule);

        // 构建新的模块配置
        Map<String, List<ApiInfo>> newModuleMap = new HashMap<>();
        apiInfoList.forEach(apiInfo -> {
            String apiName = apiInfo.getAuthType().toLowerCase();
            newModuleMap.computeIfAbsent(apiName, k -> new ArrayList<>()).add(apiInfo);
        });

        // 更新模块配置
        moduleApiMap.put(currentModule, newModuleMap);

        // 更新总的配置
        jsonMap.put("api_list", moduleApiMap);

        // 格式化并返回新的配置
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonMap);
    }
}
