package com.infrastructure.core.filter;

import com.infrastructure.pojo.RequestContext;
import com.mixi.common.component.info.transfer.UserInfoTransferHandler;
import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.IpAddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.infrastructure.config.GateWayConstant.REQUEST_CONTEXT;
import static com.mixi.common.constant.constpool.TransferConstant.*;

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

        // 创建一个Map来存储需要添加的请求头
        Map<String, String> headersToAdd = new HashMap<>();
        headersToAdd.put(USER_AGENT, exchange.getRequest().getHeaders().getFirst(USER_AGENT));

        RequestContext context = (RequestContext) exchange.getAttributes().get(REQUEST_CONTEXT);
        if (context != null) {
            TokenUserInfo tokenUserInfo = context.getTokenUserInfo();

            // 如果前面从token中拿到了用户信息，那么就走传递逻辑
            if (tokenUserInfo != null) {
                String userInfo = userInfoTransferHandler.packageUserInfo(tokenUserInfo);
                headersToAdd.put(USER_INFO, userInfo);
            }
        }

        // 更新exchange对象
        exchange = addHeadersToExchange(exchange, headersToAdd);
        return chain.filter(exchange);
    }

    private ServerWebExchange addHeadersToExchange(ServerWebExchange exchange, Map<String, String> headers) {
        return exchange.mutate()
                .request(builder -> headers.forEach(builder::header))
                .build();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}