package com.mixi.common.component.token;

import com.mixi.common.component.token.service.JwtTokenService;
import com.mixi.common.component.token.service.SaTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: TokenService工厂类，用于根据配置动态加载相应的Token服务
 * @author suifeng
 * 日期: 2024/7/13
 */
@RequiredArgsConstructor
@SuppressWarnings("all")
@Configuration
public class TokenServiceFactory {

    private static final String DEFAULT_TOKEN_SERVICE = "sa-token";

    @Value("${token.service:#{null}}")
    private String tokenServiceType;

    private final JwtTokenService jwtTokenService;
    private final SaTokenService saTokenService;

    @Bean
    public TokenService tokenService() {
        if (tokenServiceType == null || tokenServiceType.isEmpty()) {
            tokenServiceType = DEFAULT_TOKEN_SERVICE;
        }

        switch (tokenServiceType.toLowerCase()) {
            case "jwt" :
                return jwtTokenService;
            case "sa-token" :
                return saTokenService;
            default:
                throw new IllegalArgumentException("Unsupported token service type: " + tokenServiceType);
        }
    }
}