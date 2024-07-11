package com.infrastructure.utils;

import com.mixi.common.pojo.ApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * 描述: Token 工具类，用于处理 Token 的验证和角色提取逻辑
 */
@Slf4j
public class TokenUtils {

    /**
     * 从请求头中提取 Bearer Token
     */
    public static String extractTokenFromHeader(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token 请求头中的 Token
     * @return boolean 是否有效
     */
    public static boolean isTokenValid(String token) {
        return token != null && !token.isEmpty(); // 示例验证逻辑
    }

    /**
     * 从 Token 中提取角色信息
     */
    public static int[] extractRolesFromToken(String token) {
        // 示例实现，根据实际情况提取角色信息
        return new int[]{1001, 1002}; // 示例角色
    }

    /**
     * 检查用户是否具有所需的角色
     */
    public static boolean hasRequiredRoles(String token, ApiInfo apiInfo) {
        int[] userRoles = extractRolesFromToken(token);

        if (apiInfo.getRoles() == null || apiInfo.getRoles().length == 0) {
            return true;
        }

        for (int role : apiInfo.getRoles()) {
            for (int userRole : userRoles) {
                if (role == userRole) {
                    return true;
                }
            }
        }
        return false;
    }
}