package com.infrastructure.core.token;

import com.infrastructure.config.GateWayConstant;
import org.springframework.web.server.ServerWebExchange;

/**
 * 描述: 提供一些负复杂方法的算法骨架，实现一些通用方法
 * @author suifeng
 * 日期: 2024/7/12
 */
public abstract class AbstractTokenValidator implements TokenValidator {

    @Override
    public String extractTokenFromHeader(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(GateWayConstant.AUTHORIZATION);
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

        for (int role : apiRoles) {
            for (int userRole : userRoles) {
                if (role == userRole) {
                    return true;
                }
            }
        }
        return false;
    }
}