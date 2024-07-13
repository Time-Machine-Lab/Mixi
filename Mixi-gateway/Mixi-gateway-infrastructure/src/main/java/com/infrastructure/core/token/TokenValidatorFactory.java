package com.infrastructure.core.token;

import com.infrastructure.core.token.validator.JwtTokenValidator;
import com.infrastructure.core.token.validator.SaTokenValidator;
import com.mixi.common.component.token.service.JwtTokenService;
import com.mixi.common.component.token.service.SaTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 描述: token验证器工厂
 * @author suifeng
 * 日期: 2024/7/13
 */
@RequiredArgsConstructor
@SuppressWarnings("all")
@Configuration
public class TokenValidatorFactory {

    private static final String DEFAULT_TOKEN_VALIDATOR = "sa-token";

    @Value("${token.validator:#{null}}")
    private String tokenValidatorType;

    private final SaTokenValidator saTokenValidator;
    private final SaTokenService saTokenService;

    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenService jwtTokenService;

    @Bean
    @DependsOn("tokenService")
    public TokenValidator tokenValidator() {
        if (tokenValidatorType == null || tokenValidatorType.isEmpty()) {
            tokenValidatorType = DEFAULT_TOKEN_VALIDATOR;
        }

        TokenValidator tokenValidator;
        switch (tokenValidatorType.toLowerCase()) {
            case "jwt":
                tokenValidator = jwtTokenValidator;
                tokenValidator.setTokenService(jwtTokenService);
                break;
            case "sa-token":
                tokenValidator = saTokenValidator;
                tokenValidator.setTokenService(saTokenService);
                break;
            default:
                throw new IllegalArgumentException("Unsupported token validator type: " + tokenValidatorType);
        }
        return tokenValidator;
    }
}