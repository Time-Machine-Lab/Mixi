package com.infrastructure.core.auth.strategy;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.annotation.AuthStrategyType;
import com.infrastructure.core.token.TokenValidator;
import com.infrastructure.pojo.RequestContext;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.common.pojo.TokenUserInfo;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.pojo.ApiInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.infrastructure.config.GateWayConstant.REQUEST_CONTEXT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * 描述: NEED权限策略，进行 Token 验证和角色权限检查
 * @author suifeng
 * 日期: 2024/7/12
 */
@AuthStrategyType(AuthType.NEED)
@RequiredArgsConstructor
@Component
@Slf4j
public class NeedAuthStrategy implements AuthStrategy {

    private final TokenValidator tokenValidator;

    @Override
    public Mono<Void> validate(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        // 从请求头中提取token，并提取用户信息
        TokenUserInfo tokenUserInfo = tokenValidator.validateAndExtractUserInfo(tokenValidator.extractTokenFromHeader(request));

        // 判断token是否合法
        if (null == tokenUserInfo) {
            log.warn("Invalid or missing token for API: {}", request.getURI());
            return ResponseUtils.respondError(exchange, UNAUTHORIZED, "Unauthorized: Invalid or missing token.");
        }

        // 从请求上下文中获取RequestContext对象
        RequestContext context = (RequestContext) exchange.getAttributes().get(REQUEST_CONTEXT);
        ApiInfo apiInfo = context.getApiInfo();

        // 检查角色权限是否合法
        if (!tokenValidator.hasRequiredRoles(tokenUserInfo.getRoles(), apiInfo.getRoles())) {
            log.warn("Insufficient permissions for API: {}", request.getURI());
            return ResponseUtils.respondError(exchange, FORBIDDEN, "Forbidden: Insufficient permissions.");
        }

        // 将用户信息存入请求上下文
        context.setTokenUserInfo(tokenUserInfo);
        log.info("Token validation successful for API: {}", request.getURI());
        return Mono.empty();
    }
}