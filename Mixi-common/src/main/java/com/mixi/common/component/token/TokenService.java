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
     * 验证Token是否合法。
     */
    boolean isTokenValid(String token);

    /**
     * 从Token中提取用户信息。
     */
    TokenUserInfo extractUserInfoFromToken(String token);

    /**
     * 使用token进行登出
     */
    void logoutByToken(String token);

    /**
     * 使用用户id进行登出
     */
    void logoutById(String id);
}