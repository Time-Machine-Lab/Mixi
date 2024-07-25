package com.infrastructure.core.token;

import com.infrastructure.config.GateWayConstant;
import com.mixi.common.component.token.TokenService;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述: 实现一些通用方法，提供一些负复杂方法的算法骨架
 * @author suifeng
 * 日期: 2024/7/12
 */
public abstract class AbstractTokenValidator implements TokenValidator {

    protected TokenService tokenService;

    @Override
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public String extractTokenFromHeader(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getFirst(GateWayConstant.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(GateWayConstant.BEARER)) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @Override
    public boolean hasRequiredRoles(int[] userRoles, int[] apiRoles) {

        // 如果这个接口是没得任何权限限制的，就直接放行
        if (apiRoles == null || apiRoles.length == 0) {
            return true;
        }

        Set<Integer> userRolesSet = new HashSet<>();
        for (int role : userRoles) {
            userRolesSet.add(role);
        }

        // 检查用户是否具有所需的角色
        for (int role : apiRoles) {
            if (userRolesSet.contains(role)) {
                return true;
            }
        }
        return false;
    }
}