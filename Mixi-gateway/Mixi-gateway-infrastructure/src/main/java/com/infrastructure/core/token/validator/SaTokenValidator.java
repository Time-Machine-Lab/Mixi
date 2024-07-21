package com.infrastructure.core.token.validator;

import com.infrastructure.core.token.AbstractTokenValidator;
import com.mixi.common.pojo.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 描述: 使用Sa-Token实现Token验证
 * @author suifeng
 * 日期: 2024/7/12
 */
@RequiredArgsConstructor
@Component
public class SaTokenValidator extends AbstractTokenValidator {

    @Override
    public TokenUserInfo validateAndExtractUserInfo(String token) {
        return tokenService.validateAndExtractUserInfo(token);
    }
}