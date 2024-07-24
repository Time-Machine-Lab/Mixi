package com.infrastructure.core.auth.handler.before;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.annotation.BeforeHandler;
import com.infrastructure.core.token.TokenValidator;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.utils.RCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述: 游客接口前置处理器
 * @author suifeng
 * 日期: 2024/7/23
 */
@BeforeHandler("TouristBeforeHandler")
@AllArgsConstructor
@Component
@Slf4j
public class TouristBeforeHandler implements AuthStrategy {

    private final TokenValidator tokenValidator;

    @Override
    public Mono<Void> before(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        // 从请求头中获取token
        String token = tokenValidator.extractTokenFromHeader(request);

        // 如果没有携带token
        if (token == null) {
            log.info("Unauthorized request: Missing token.");

            // 直接返回特殊状态码告诉前端
            return ResponseUtils.respondError(exchange, RCode.VISITOR_REDIRECTION);
        }

        return AuthStrategy.super.before(exchange);
    }
}
