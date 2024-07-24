package com.infrastructure.core.filter;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.AuthStrategyFactory;
import com.infrastructure.pojo.RequestContext;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.common.pojo.ApiInfo;
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

            ApiInfo apiInfo = context.getApiInfo();

            // 获取权限类型
            AuthType authType = AuthType.valueOf(apiInfo.getAuthType());

            // 获取对应的策略处理器
            AuthStrategy authStrategy = authStrategyFactory.getStrategy(authType);

            // 获取前置处理器
            AuthStrategy beforeHandler = authStrategyFactory.getBeforeHandler(apiInfo.getBeforeHandler());

            // 获取后置处理器
            AuthStrategy afterHandler = authStrategyFactory.getAfterHandler(apiInfo.getAfterHandler());

            // 依次执行处理器
            return beforeHandler.before(exchange)
                    .then(authStrategy.validate(exchange))
                    .then(afterHandler.after(exchange))
                    .then(chain.filter(exchange))
                    .onErrorResume(e -> {
                        log.error("Error during filter execution: ", e);
                        return ResponseUtils.respondError(exchange, INTERNAL_SERVER_ERROR, "Internal Server Error");
                    });
        }

        return ResponseUtils.respondError(exchange, INTERNAL_SERVER_ERROR, "RequestContext not found");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}