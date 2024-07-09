package com.mixi.common.pojo;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * 描述: 要上云的接口信息
 * @author suifeng
 * 日期: 2024/7/6
 */
@AllArgsConstructor
@Data
public class ApiInfo {

    // 权限类型
    private String authType;

    // 请求路径
    private String url;

    // 来着哪个模块
    private String module;

    // 来自哪个类
    private String type;

    // 方法名
    private String method;

    // 请求的方式(GET/POST/PUT/DELETE)
    private String requestMethod;

    // 身份维度
    private int[] roles;

    public static ApiInfo build(ApiAuth apiAuth, RequestMappingInfo requestMappingInfo, Method method, Class<?> beanType) {
        String moduleName = extractModuleName(beanType.getPackage().getName());
        String url = getUrlFromRequestMappingInfo(requestMappingInfo);
        String requestMethod = getRequestMethodFromRequestMappingInfo(requestMappingInfo);
        String authType = determineAuthType(apiAuth);
        String handlerMethod = method.getName();

        // 提取身份维度数组
        int[] roles = apiAuth.roles();

        // 如果不是NEED类型的话，连云端的空数组都不必展示，防止用户进行修改
        if (!AuthType.NEED.name().equals(authType)) {
            roles = null;
        }

        return new ApiInfo(authType, url, moduleName, beanType.getSimpleName(), handlerMethod, requestMethod, roles);
    }

    private static String extractModuleName(String packageName) {
        String[] parts = packageName.split("\\.");
        return parts.length > 2 ? parts[2] : packageName;
    }

    private static String getUrlFromRequestMappingInfo(RequestMappingInfo requestMappingInfo) {
        Set<String> patterns = Objects.requireNonNull(requestMappingInfo.getPatternsCondition()).getPatterns();
        return patterns.isEmpty() ? "" : patterns.iterator().next();
    }

    private static String getRequestMethodFromRequestMappingInfo(RequestMappingInfo requestMappingInfo) {
        Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
        return methods.isEmpty() ? "GET" : methods.iterator().next().name();
    }

    private static String determineAuthType(ApiAuth apiAuth) {
        // 只要添加了身份维度的注解，就统一转换为NEED权限
        if (apiAuth.roles().length > 0 && apiAuth.value() != AuthType.NEED) {
            return AuthType.NEED.name();
        }
        return apiAuth.value().name();
    }
}