package com.mixi.common.component.token.service;

import com.mixi.common.component.token.AbstractTokenService;
import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.stereotype.Service;

/**
 * 描述: 使用Jwt实现Token服务
 * @author suifeng
 * 日期: 2024/7/13
 */
@Service
public class JwtTokenService extends AbstractTokenService {

    @Override
    public String loginAndGenerateToken(TokenUserInfo tokenUserInfo) {
        return "";
    }

    @Override
    public TokenUserInfo validateAndExtractUserInfo(String token) {
        return null;
    }

    @Override
    public void logoutByToken(String token) {

    }
}