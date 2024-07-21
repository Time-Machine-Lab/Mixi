package com.mixi.common.component.token;

import com.mixi.common.pojo.TokenUserInfo;

/**
 * 描述: Token服务抽象类
 * @author suifeng
 * 日期: 2024/7/13
 */
public abstract class AbstractTokenService implements TokenService {

    @Override
    public String loginAndGenerateToken(String userId, String username, int[] roles) {
        return loginAndGenerateToken(new TokenUserInfo(userId, username, roles, null));
    }

    @Override
    public String loginAndGenerateToken(String userId, String username) {
        return loginAndGenerateToken(userId, username, new int[0]);
    }
}