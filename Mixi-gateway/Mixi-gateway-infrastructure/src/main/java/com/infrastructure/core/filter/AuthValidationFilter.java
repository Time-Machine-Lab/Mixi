package com.infrastructure.core.filter;

import com.infrastructure.pojo.RequestContext;
import com.infrastructure.utils.ResponseUtils;
import com.infrastructure.utils.TokenUtils;
import com.mixi.common.pojo.ApiInfo;
import com.mixi.common.annotation.auth.AuthType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述: 权限验证过滤器，检查请求是否具有适当的权限和身份维度
 *  - INNER 类型：直接拒绝请求
 *  - NOT 类型：直接放行
 *  - NEED 类型：进行 Token 验证和角色权限检查
 * @author suifeng
 * 日期: 2024/7/11 
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthValidationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        RequestContext context = (RequestContext) exchange.getAttributes().get("requestContext");

        if (context != null) {
            ApiInfo apiInfo = context.getApiInfo();

            // INNER 类型，直接拒绝请求
            if (AuthType.INNER.name().equalsIgnoreCase(apiInfo.getAuthType())) {
                return ResponseUtils.respondError(exchange, HttpStatus.FORBIDDEN, "Access denied: Internal API.");
            }

            // NOT 类型，不需要权限验证，直接放行
            if (AuthType.NOT.name().equalsIgnoreCase(apiInfo.getAuthType())) {
                return chain.filter(exchange);
            }

            // NEED 类型
            if (AuthType.NEED.name().equalsIgnoreCase(apiInfo.getAuthType())) {

                // 从请求头中提取 token
                String token = TokenUtils.extractTokenFromHeader(exchange);

                // 判断 token 是否合法
                if (!TokenUtils.isTokenValid(token)) {
                    return ResponseUtils.respondError(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized: Invalid or missing token.");
                }

                // 检查角色权限
                if (!TokenUtils.hasRequiredRoles(token, apiInfo)) {
                    return ResponseUtils.respondError(exchange, HttpStatus.FORBIDDEN, "Forbidden: Insufficient permissions.");
                }

                // 将 token 存入上下文对象
                context.setToken(token);
            }

            // 继续过滤链
            return chain.filter(exchange);
        }
        return ResponseUtils.respondError(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "RequestContext not found");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}