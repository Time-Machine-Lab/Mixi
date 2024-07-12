package com.infrastructure.core.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述: NOT权限策略，直接放行
 * @author suifeng
 * 日期: 2024/7/12
 */
@Component
@Slf4j
public class NotAuthStrategy implements AuthStrategy {
    @Override
    public Mono<Void> validate(ServerWebExchange exchange) {
        log.info("No authentication required for API: {}", exchange.getRequest().getURI());
        return Mono.empty();
    }
}