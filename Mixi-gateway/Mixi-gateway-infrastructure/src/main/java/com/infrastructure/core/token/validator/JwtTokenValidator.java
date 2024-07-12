package com.infrastructure.core.token.validator;

import com.infrastructure.core.token.AbstractTokenValidator;
import com.infrastructure.pojo.UserInfo;
import org.springframework.stereotype.Component;

/**
 * 描述: 使用JWT实现Token验证
 * @author suifeng
 * 日期: 2024/7/12
 */
@Component
public class JwtTokenValidator extends AbstractTokenValidator {

    @Override
    public boolean isTokenValid(String token) {
        // 使用JWT的逻辑验证Token
        return token != null && !token.isEmpty(); // 示例验证逻辑
    }

    @Override
    public UserInfo extractUserInfoFromToken(String token) {
        // 使用JWT的逻辑从Token中提取用户信息
        return new UserInfo("userId123", "username123", new int[]{1001, 1002});
    }
}