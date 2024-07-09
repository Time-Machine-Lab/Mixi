package com.mixi.common.component.cloud;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mixi.common.pojo.ApiInfo;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述: 云接口配置文件构建器
 * @author suifeng
 * 日期: 2024/7/7
 */
@SuppressWarnings("all")
@Component
public class CloudApiConfigBuilder {

    /**
     *  根据原始的配置文件和新启动项目的Api集合构建新的配置文件
     */
    public String build(String existConfig, List<ApiInfo> apiInfoList) throws Exception {

        // 解析原始的配置文件
        Map<String, List<ApiInfo>> jsonMap = parseJsonConfigToMap(existConfig);

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

        // 排除null字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonMap);
    }

    /**
     *  解析原始的JSON配置文件
     */
    private Map<String, List<ApiInfo>> parseJsonConfigToMap(String config) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return config == null ? new HashMap<>() : objectMapper.readValue(config, Map.class);
    }
}
