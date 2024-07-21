package com.mixi.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 用户信息类，用于存储从Token中提取的用户信息
 * @author suifeng
 * 日期: 2024/7/12
 */
@SuppressWarnings("all")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenUserInfo {

    private String userId;
    private String username;
    private int[] roles = new int[0];

    // 扩展字段
    private Map<String, Object> additionalFields = new HashMap<>();

    public void addField(String key, Object value) {
        additionalFields.put(key, value);
    }

    public Object getField(String key) {
        return additionalFields.get(key);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String username;
        private int[] roles = new int[0];
        private Map<String, Object> additionalFields = new HashMap<>();

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder roles(int[] roles) {
            this.roles = roles;
            return this;
        }

        public Builder addField(String key, Object value) {
            this.additionalFields.put(key, value);
            return this;
        }

        public Builder additionalFields(Map<String, Object> additionalFields) {
            this.additionalFields.putAll(additionalFields);
            return this;
        }

        public TokenUserInfo build() {
            if (userId == null || username == null) {
                throw new IllegalArgumentException("User ID and Username cannot be null");
            }
            TokenUserInfo tokenUserInfo = new TokenUserInfo();
            tokenUserInfo.setUserId(userId);
            tokenUserInfo.setUsername(username);
            tokenUserInfo.setRoles(roles);
            tokenUserInfo.setAdditionalFields(additionalFields);
            return tokenUserInfo;
        }
    }

}