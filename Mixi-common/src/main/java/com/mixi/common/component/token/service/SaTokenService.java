package com.mixi.common.component.token.service;

import cn.dev33.satoken.stp.StpUtil;
import com.mixi.common.component.token.AbstractTokenService;

import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 使用Sa-Token实现Token服务
 * @author suifeng
 * 日期: 2024/7/13
 */
@Service
public class SaTokenService extends AbstractTokenService {

    @Override
    public String loginAndGenerateToken(TokenUserInfo tokenUserInfo) {

        // 用户id作为登录key进行登录
        StpUtil.login(tokenUserInfo.getUserId());

        // 分别将用户名和角色权限加入Session会话
        StpUtil.getSession().set("username", tokenUserInfo.getUsername());
        StpUtil.getSession().set("roles", tokenUserInfo.getRoles());

        // 将额外字段加入Session会话
        tokenUserInfo.getAdditionalFields().forEach((key, value) -> StpUtil.getSession().set(key, value));

        // 返回登录token
        return StpUtil.getTokenValue();
    }

    @Override
    public boolean isTokenValid(String token) {
        return StpUtil.isLogin();
    }

    @Override
    public TokenUserInfo extractUserInfoFromToken(String token) {

        // 从token中提取基本信息
        String userId = StpUtil.getLoginIdAsString();
        String username = (String) StpUtil.getSession().get("username");
        int[] roles = (int[]) StpUtil.getSession().get("roles");

        // 提取额外字段
        Map<String, Object> additionalFields = new HashMap<>();
        StpUtil.getSession().keys().forEach(key -> {
            if (!key.equals("username") && !key.equals("roles")) {
                additionalFields.put(key, StpUtil.getSession().get(key));
            }
        });

        return TokenUserInfo.builder()
                .userId(userId)
                .username(username)
                .roles(roles)
                .additionalFields(additionalFields)
                .build();
    }

    @Override
    public void logoutByToken(String token) {
        StpUtil.logoutByTokenValue(token);
    }

    @Override
    public void logoutById(String id) {
        StpUtil.logout(id);
    }
}