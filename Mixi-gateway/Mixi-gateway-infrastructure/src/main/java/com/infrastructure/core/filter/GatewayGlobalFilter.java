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
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.infrastructure.config.GateWayConstant.REQUEST_CONTEXT;
import static com.infrastructure.config.GateWayConstant.REQUEST_PREFIX;
import static org.springframework.http.HttpStatus.*;

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

        if (requestMethod == null) {
            return ResponseUtils.respondError(exchange, METHOD_NOT_ALLOWED, "Method not allowed: " + requestUri);
        }

        // 从请求头中提取前缀参数
        String prefixHeader = exchange.getRequest().getHeaders().getFirst(REQUEST_PREFIX);

        // 判断这个前缀参数是否是原始请求路径中的开头，并判断是否是空的
        if (prefixHeader == null || prefixHeader.isEmpty() || !requestUri.startsWith(prefixHeader)) {
            return ResponseUtils.respondError(exchange, BAD_REQUEST, "Invalid or missing prefix header");
        }

        // 根据这个前缀获取对应的ApiInfo
        Optional<ApiInfo> apiInfoOptional = gatewayManager.getApiInfo(prefixHeader, requestMethod.name());

        if (apiInfoOptional.isPresent()) {
            ApiInfo apiInfo = apiInfoOptional.get();
            String moduleName = apiInfo.getModule();

            // 检查模块是否支持
            if (!gatewayManager.isModuleSupported(moduleName)) {
                return ResponseUtils.respondError(exchange, FORBIDDEN, "Module not supported: " + moduleName);
            }

            // 创建RequestContext并传递给下一个过滤器
            RequestContext context = new RequestContext(apiInfo, requestUri,null);
            exchange.getAttributes().put(REQUEST_CONTEXT, context);

            // 继续过滤链
            return chain.filter(exchange);
        } else {
            // API未找到
            return ResponseUtils.respondError(exchange, NOT_FOUND, "API not found for URI: " + requestUri);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
