package com.infrastructure.core.filter;

import com.infrastructure.core.manager.GateWayManager;
import com.infrastructure.pojo.RequestContext;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 描述: 网关全局过滤器，检查路径是否存在，检查模块支持情况和请求方法
 * @author suifeng
 * 日期: 2024/7/11 
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    private final GateWayManager gatewayManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUri = exchange.getRequest().getURI().getPath();
        HttpMethod requestMethod = exchange.getRequest().getMethod();

        // 获取对应的ApiInfo
        Optional<ApiInfo> apiInfoOptional = gatewayManager.getApiInfo(requestUri);

        if (apiInfoOptional.isPresent()) {
            ApiInfo apiInfo = apiInfoOptional.get();
            String moduleName = apiInfo.getModule();

            // 检查模块是否支持
            if (!gatewayManager.isModuleSupported(moduleName)) {
                return ResponseUtils.respondError(exchange, HttpStatus.FORBIDDEN, "Module not supported: " + moduleName);
            }

            // 检查请求方法是否匹配
            if (requestMethod == null || !requestMethod.name().equalsIgnoreCase(apiInfo.getRequestMethod())) {
                return ResponseUtils.respondError(exchange, HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed: " + requestMethod);
            }

            // 创建RequestContext并传递给下一个过滤器
            RequestContext context = new RequestContext(apiInfo, requestUri,null);
            exchange.getAttributes().put("requestContext", context);

            // 继续过滤链
            return chain.filter(exchange);
        } else {
            // API未找到
            return ResponseUtils.respondError(exchange, HttpStatus.NOT_FOUND, "API not found for URI: " + requestUri);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
