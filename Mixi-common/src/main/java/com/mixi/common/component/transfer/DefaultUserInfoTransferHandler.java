package com.mixi.common.component.transfer;

import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.mixi.common.constant.constpool.TransferConstant.USER_INFO;

/**
 * 描述: 默认实现的传递与接收逻辑
 * @author suifeng
 * 日期: 2024/7/17
 */
@Component
public class DefaultUserInfoTransferHandler implements UserInfoTransferHandler {

    /**
     * 将用户信息传递给下游微服务
     */
    @Override
    public void passUserInfo(ServerWebExchange exchange, TokenUserInfo tokenUserInfo) {
        if (tokenUserInfo != null) {
            StringBuilder userInfoBuilder = new StringBuilder();

            // 先拼接用户ID，用户名，权限
            userInfoBuilder.append(tokenUserInfo.getUserId()).append(" ")
                    .append(tokenUserInfo.getUsername()).append(" ")
                    .append(Arrays.toString(tokenUserInfo.getRoles()));

            // 拼接扩展字段
            for (Map.Entry<String, Object> entry : tokenUserInfo.getAdditionalFields().entrySet()) {
                userInfoBuilder.append(" ").append(entry.getKey()).append("=").append(entry.getValue());
            }

            exchange.getResponse().getHeaders().add(USER_INFO, userInfoBuilder.toString());
        }
    }

    /**
     * 从请求中提取用户信息
     */
    @Override
    public TokenUserInfo extractUserInfo(HttpServletRequest request) {
        String userInfo = request.getHeader(USER_INFO);
        if (userInfo != null && !userInfo.isEmpty()) {
            return convertStringToTokenUserInfo(userInfo);
        }
        return null;
    }

    private TokenUserInfo convertStringToTokenUserInfo(String userInfo) {
        String[] parts = userInfo.split(" ");
        String userId = parts[0];
        String username = parts[1];
        int[] roles = Arrays.stream(parts[2].substring(1, parts[2].length() - 1).split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        Map<String, Object> additionalFields = new HashMap<>();
        for (int i = 3; i < parts.length; i++) {
            String[] fieldParts = parts[i].split("=");
            additionalFields.put(fieldParts[0], fieldParts[1]);
        }

        return TokenUserInfo.builder()
                .userId(userId)
                .username(username)
                .roles(roles)
                .additionalFields(additionalFields)
                .build();
    }
}