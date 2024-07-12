package com.infrastructure.core.filter;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.AuthStrategyFactory;
import com.infrastructure.pojo.RequestContext;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.annotation.auth.AuthType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.infrastructure.config.GateWayConstant.REQUEST_CONTEXT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 描述: 权限验证过滤器，检查请求是否具有适当的权限和身份维度
 * @author suifeng
 * 日期: 2024/7/11 
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthValidationFilter implements GlobalFilter, Ordered {

    private final AuthStrategyFactory authStrategyFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        RequestContext context = (RequestContext) exchange.getAttributes().get(REQUEST_CONTEXT);

        if (context != null) {
            // 获取权限类型
            AuthType authType = AuthType.valueOf(context.getApiInfo().getAuthType());

            // 获取对应的策略并执行验证
            AuthStrategy authStrategy = authStrategyFactory.getStrategy(authType);
            return authStrategy.validate(exchange).then(chain.filter(exchange));
        }

        return ResponseUtils.respondError(exchange, INTERNAL_SERVER_ERROR, "RequestContext not found");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}