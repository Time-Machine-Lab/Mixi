package com.mixi.common.component.token.service;

import cn.dev33.satoken.stp.StpUtil;
import com.mixi.common.component.info.transfer.UserInfoTransferHandler;
import com.mixi.common.component.token.AbstractTokenService;
import com.mixi.common.pojo.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 描述: 使用Sa-Token实现Token服务
 * @author suifeng
 * 日期: 2024/7/13
 */
@RequiredArgsConstructor
@Service
public class SaTokenService extends AbstractTokenService {

    private final UserInfoTransferHandler userInfoTransferHandler;

    @Override
    public String loginAndGenerateToken(TokenUserInfo tokenUserInfo) {

        // 将用户信息拼接成一个长字符串
        String userInfoString = userInfoTransferHandler.packageUserInfo(tokenUserInfo);

        // 使用拼接后的字符串作为登录标识
        StpUtil.login(userInfoString);

        // 返回登录的token
        return StpUtil.getTokenValue();
    }

    @Override
    public TokenUserInfo validateAndExtractUserInfo(String token) {
        if (null == token || token.isEmpty()) {
            return null;
        }

        // 获取登录标识
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        if (loginIdByToken == null) {
            return null;
        }

        // 解析用户信息字符串
        return userInfoTransferHandler.extractUserInfo((String) loginIdByToken);
    }

    @Override
    public void logoutByToken(String token) {
        StpUtil.logoutByTokenValue(token);
    }
}