package com.mixi.common.pojo;

import com.mixi.common.annotation.auth.ApiAuth;
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

    public static ApiInfo buildApiInfo(ApiAuth apiAuth, RequestMappingInfo requestMappingInfo, Method method, Class<?> beanType) {
        String moduleName = extractModuleName(beanType.getPackage().getName());
        String url = getUrlFromRequestMappingInfo(requestMappingInfo);
        String requestMethod = getRequestMethodFromRequestMappingInfo(requestMappingInfo);
        String authType = apiAuth.value().name();
        String handlerMethod = method.getName();
        return new ApiInfo(authType, url, moduleName, beanType.getSimpleName(), handlerMethod, requestMethod);
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
}