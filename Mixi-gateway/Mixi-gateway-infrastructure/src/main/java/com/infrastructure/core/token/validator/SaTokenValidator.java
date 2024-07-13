package com.infrastructure.core.token.validator;

import com.infrastructure.core.token.AbstractTokenValidator;
import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.stereotype.Component;

/**
 * 描述: 使用Sa-Token实现Token验证
 * @author suifeng
 * 日期: 2024/7/12
 */
@Component
public class SaTokenValidator extends AbstractTokenValidator {

    @Override
    public boolean isTokenValid(String token) {
        return tokenService.isTokenValid(token);
    }

    @Override
    public TokenUserInfo extractUserInfoFromToken(String token) {
        return tokenService.extractUserInfoFromToken(token);
    }
}