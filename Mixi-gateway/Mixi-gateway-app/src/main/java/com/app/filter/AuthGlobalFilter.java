package com.app.filter;

import com.app.config.AuthProperties;
import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述: 全局请求拦截器
 * @author suifeng
 * 日期: 2024/7/5
 */
@RequiredArgsConstructor
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 直接放行不需要进行token验证的路径
        if (isExclude(path)) return chain.filter(exchange);


        return null;
    }

    // 判断是否是放行路径
    private boolean isExclude(String string) {
        for (String excludePath : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
