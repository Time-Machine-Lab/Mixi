package com.infrastructure.core.auth.strategy;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.annotation.AuthStrategyType;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.annotation.auth.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * 描述: INNER权限策略，直接拒绝请求
 * @author suifeng
 * 日期: 2024/7/12
 */
@AuthStrategyType(AuthType.INNER)
@Component
@Slf4j
public class InnerAuthStrategy implements AuthStrategy {
    @Override
    public Mono<Void> validate(ServerWebExchange exchange) {
        log.info("Access denied for internal API: {}", exchange.getRequest().getURI());
        return ResponseUtils.respondError(exchange, FORBIDDEN, "Access denied: Internal API.");
    }
}