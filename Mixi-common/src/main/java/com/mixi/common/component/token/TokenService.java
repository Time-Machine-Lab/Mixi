package com.mixi.common.component.token;


import com.mixi.common.pojo.TokenUserInfo;

/**
 * 描述: 定义了Token服务的标准方法
 * @author suifeng
 * 日期: 2024/7/13
 */
public interface TokenService {

    /**
     * 登录并生成Token。
     */
    String loginAndGenerateToken(TokenUserInfo tokenUserInfo);
    String loginAndGenerateToken(String userId, String username);
    String loginAndGenerateToken(String userId, String username, int[] roles);

    /**
     * 验证并解析出用户信息。
     */
    TokenUserInfo validateAndExtractUserInfo(String token);

    /**
     * 使用token进行登出
     */
    void logoutByToken(String token);
}