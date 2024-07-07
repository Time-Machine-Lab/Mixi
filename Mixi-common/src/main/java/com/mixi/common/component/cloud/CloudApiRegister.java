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
import java.util.stream.Collectors;

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
        Map<String, List<ApiInfo>> jsonMap = parseJsonConfig(existConfig);

        // 构建新的配置文件
        String newConfig = buildApiConfig(jsonMap, apiInfoList);

        // 重新发布，更新云端配置文件
        configService.publishConfig(nacosDataId, cloudApiProperties.getGroup(), newConfig, ConfigType.JSON.getType());
    }

    private Map<String, List<ApiInfo>> parseJsonConfig(String config) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return config == null ? new HashMap<>() : objectMapper.readValue(config, Map.class);
    }

    private String buildApiConfig(Map<String, List<ApiInfo>> jsonMap, List<ApiInfo> apiInfoList) throws Exception {

        // 获取当前模块名
        String currentModule = apiInfoList.get(0).getModule();

        // 删除旧的模块配置
        jsonMap.remove(currentModule);

        // 将API信息按照模块名分组
        Map<String, List<ApiInfo>> groupedByModule = apiInfoList.stream()
                .collect(Collectors.groupingBy(ApiInfo::getModule));

        // 对每个模块的API信息进行排序并更新
        groupedByModule.forEach((module, apiInfos) -> {
            List<ApiInfo> sortedApiInfos = apiInfos.stream()
                    .sorted(Comparator.comparing(ApiInfo::getAuthType))
                    .collect(Collectors.toList());
            jsonMap.put(module, sortedApiInfos);
        });

        // 格式化并返回新的配置
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonMap);
    }
}
