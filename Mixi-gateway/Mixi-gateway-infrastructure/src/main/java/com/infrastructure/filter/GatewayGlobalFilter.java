package com.infrastructure.filter;

import com.infrastructure.core.GateWayManager;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 描述: 网关全局过滤器
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

        // 获取对应的ApiInfo
        Optional<ApiInfo> apiInfoOptional = gatewayManager.getApiInfo(requestUri);

        if (apiInfoOptional.isPresent()) {
            ApiInfo apiInfo = apiInfoOptional.get();
            String moduleName = apiInfo.getModule();

            if (gatewayManager.isModuleSupported(moduleName)) {
                return chain.filter(exchange);
            } else {
                log.warn("Module not supported: {}", moduleName);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
        } else {
            log.warn("API not found for URI: {}", requestUri);
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
