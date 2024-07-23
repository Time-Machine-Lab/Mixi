package com.infrastructure.core.auth;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述: 接口权限校验策略接口
 * @author suifeng
 * 日期: 2024/7/12
 */
public interface AuthStrategy {

    default Mono<Void> before(ServerWebExchange exchange) {
        return Mono.empty();
    }

    default Mono<Void> validate(ServerWebExchange exchange) {
        return Mono.empty();
    }

    default Mono<Void> after(ServerWebExchange exchange) {
        return Mono.empty();
    }
}