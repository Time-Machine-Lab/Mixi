package com.infrastructure.core.filter;

import com.infrastructure.pojo.RequestContext;
import com.mixi.common.component.info.transfer.UserInfoTransferHandler;
import com.mixi.common.pojo.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.infrastructure.config.GateWayConstant.REQUEST_CONTEXT;
import static com.mixi.common.constant.constpool.TransferConstant.USER_INFO;

/**
 * 描述: 收尾过滤器
 *  - 1. 将用户信息传递给下游微服务
 * @author suifeng
 * 日期: 2024/7/17
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class FinalProcessingFilter implements GlobalFilter, Ordered {

    private final UserInfoTransferHandler userInfoTransferHandler;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        RequestContext context = (RequestContext) exchange.getAttributes().get(REQUEST_CONTEXT);

        if (context != null) {
            TokenUserInfo tokenUserInfo = context.getTokenUserInfo();

            // 如果前面从token中拿到了用户信息，那么就走传递逻辑
            if (tokenUserInfo != null) {
                String userInfo = userInfoTransferHandler.packageUserInfo(tokenUserInfo);

                ServerWebExchange webExchange = exchange.mutate()
                        .request(builder -> builder.header(USER_INFO, userInfo))
                        .build();
                return chain.filter(webExchange);
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}