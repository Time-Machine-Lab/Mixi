package com.mixi.common.component.cloud;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.pojo.ApiInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述: 云接口收集器，负责在其他模块启动的时候自动收集
 *       RequestMapping(控制层)下的接口信息
 * @author suifeng
 * 日期: 2024/7/6
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class CloudApiCollector implements InitializingBean {

    @Getter
    private List<ApiInfo> apiInfoList;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Value("${spring.cloud.api:false}")
    private boolean OPEN_FLAG;

    @Override
    public void afterPropertiesSet() {
        if (!OPEN_FLAG) return;
        try {
            if (requestMappingHandlerMapping == null) {
                log.error("RequestMappingHandlerMapping is null");
                return;
            }
            apiInfoList = collectApiInfo();
            apiInfoList.forEach(apiInfo -> log.info("API Info: {}", apiInfo));
        } catch (Exception e) {
            log.error("An error occurred while collecting interface information: ", e);
        }
    }

    /**
     *  从requestMapping中收集ApiInfo到一个集合中
     */
    private List<ApiInfo> collectApiInfo() {
        List<ApiInfo> apiInfoList = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Method method = handlerMethod.getMethod();
            Class<?> beanType = handlerMethod.getBeanType();

            // 拿到接口的权限注解
            ApiAuth apiAuth = getApiAuthAnnotation(method, beanType);

            // 没标ApiAuth注解的就不上云
            if (apiAuth != null) {
                apiInfoList.add(ApiInfo.build(apiAuth, requestMappingInfo, method, beanType));
            }
        }
        return apiInfoList;
    }

    /**
     *  获取接口上的ApiAuth注解
     */
    private ApiAuth getApiAuthAnnotation(Method method, Class<?> beanType) {
        // 优先处理方法上的注解
        if (method.isAnnotationPresent(ApiAuth.class)) {
            return method.getAnnotation(ApiAuth.class);
        }

        // 如果方法上没有注解，则处理类上的注解
        else if (beanType.isAnnotationPresent(ApiAuth.class)) {
            return beanType.getAnnotation(ApiAuth.class);
        }
        return null;
    }
}
