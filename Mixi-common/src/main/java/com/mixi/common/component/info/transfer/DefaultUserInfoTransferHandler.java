package com.mixi.common.component.info.transfer;

import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述: 默认实现的传递与接收逻辑
 * @author suifeng
 * 日期: 2024/7/17
 */
@Component
public class DefaultUserInfoTransferHandler implements UserInfoTransferHandler {

    /**
     * 将用户信息包装成userInfo字符串
     */
    @Override
    public String packageUserInfo(TokenUserInfo tokenUserInfo) {
        if (tokenUserInfo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(tokenUserInfo.getUserId())
                    .append("|")
                    .append(tokenUserInfo.getUsername())
                    .append("|");

            // 处理角色
            if (tokenUserInfo.getRoles() != null && tokenUserInfo.getRoles().length > 0) {
                sb.append(Arrays.stream(tokenUserInfo.getRoles())
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(",")));
            }
            sb.append("|");

            // 处理扩展字段
            tokenUserInfo.getAdditionalFields().forEach((key, value) -> {
                sb.append(key).append("=").append(value).append(";");
            });

            return sb.toString();
        }
        return null;
    }

    /**
     * 从userInfo字符串中提取用户信息
     */
    @Override
    public TokenUserInfo extractUserInfo(String userInfo) {
        if (userInfo != null && !userInfo.isEmpty()) {
            String[] parts = userInfo.split("\\|");
            String userId = parts[0];
            String username = parts[1];

            // 处理角色
            int[] roles = new int[0];
            if (parts.length > 2 && !parts[2].isEmpty()) {
                roles = Arrays.stream(parts[2].split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            // 处理扩展字段
            Map<String, Object> additionalFields = new HashMap<>();
            if (parts.length > 3 && !parts[3].isEmpty()) {
                String[] fields = parts[3].split(";");
                for (String field : fields) {
                    String[] keyValue = field.split("=");
                    if (keyValue.length == 2) {
                        additionalFields.put(keyValue[0], keyValue[1]);
                    }
                }
            }

            return TokenUserInfo.builder()
                    .userId(userId)
                    .username(username)
                    .roles(roles)
                    .additionalFields(additionalFields)
                    .build();
        }
        return null;
    }
}