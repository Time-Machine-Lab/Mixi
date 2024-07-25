package com.infrastructure.core.auth.handler;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.annotation.AfterHandler;
import com.infrastructure.core.auth.annotation.BeforeHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述: 默认前置后置处理器
 * @author suifeng
 * 日期: 2024/7/23
 */
@BeforeHandler("NIL")
@AfterHandler("NIL")
@Component
public class DefaultHandler implements AuthStrategy {

    @Override
    public Mono<Void> before(ServerWebExchange exchange) {
        return AuthStrategy.super.before(exchange);
    }

    @Override
    public Mono<Void> after(ServerWebExchange exchange) {
        return AuthStrategy.super.after(exchange);
    }
}
