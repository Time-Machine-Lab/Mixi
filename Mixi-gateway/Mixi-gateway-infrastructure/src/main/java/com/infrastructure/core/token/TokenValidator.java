package com.infrastructure.core.token;

import com.mixi.common.component.token.TokenService;
import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 描述: 定义了Token验证的标准方法
 * @author suifeng
 * 日期: 2024/7/12
 */
public interface TokenValidator {

    /**
     * 设置Token服务。
     */
    void setTokenService(TokenService tokenService);

    /**
     * 从请求头中提取Token。
     */
    String extractTokenFromHeader(ServerHttpRequest request);

    /**
     * 从Token中提取用户信息。
     */
    TokenUserInfo validateAndExtractUserInfo(String token);

    /**
     * 检查用户是否具有所需的角色权限。
     */
    boolean hasRequiredRoles(int[] userRoles, int[] apiRoles);
}