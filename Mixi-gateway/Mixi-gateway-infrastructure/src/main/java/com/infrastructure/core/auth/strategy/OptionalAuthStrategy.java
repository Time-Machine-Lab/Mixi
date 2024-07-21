package com.infrastructure.core.auth.strategy;

import com.infrastructure.core.auth.AuthStrategy;
import com.infrastructure.core.auth.AuthStrategyType;
import com.infrastructure.core.token.TokenValidator;
import com.infrastructure.pojo.RequestContext;
import com.infrastructure.utils.ResponseUtils;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.common.pojo.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.infrastructure.config.GateWayConstant.REQUEST_CONTEXT;

/**
 * 描述: OPTIONAL权限策略，有TOKEN就解析并传递，没有就放行
 * @author suifeng
 * 日期: 2024/7/17
 */
@AuthStrategyType(AuthType.OPTIONAL)
@RequiredArgsConstructor
@Component
@Slf4j
public class OptionalAuthStrategy implements AuthStrategy {

    private final TokenValidator tokenValidator;

    @Override
    public Mono<Void> validate(ServerWebExchange exchange) {

        // 从请求头中提取token，并提取用户信息
        TokenUserInfo tokenUserInfo = tokenValidator.validateAndExtractUserInfo(tokenValidator.extractTokenFromHeader(exchange));

        // 如果携带了有效TOKEN
        if (null != tokenUserInfo) {
            // 将用户信息存入请求上下文
            RequestContext context = (RequestContext) exchange.getAttributes().get(REQUEST_CONTEXT);
            if (context != null) {
                context.setTokenUserInfo(tokenUserInfo);
                log.info("Token validation successful for API: {}", exchange.getRequest().getURI());
            }
        } else {
            log.info("No valid token found, proceeding without authentication for API: {}", exchange.getRequest().getURI());
        }

        // 无论是否有TOKEN，均继续放行
        return Mono.empty();
    }
}