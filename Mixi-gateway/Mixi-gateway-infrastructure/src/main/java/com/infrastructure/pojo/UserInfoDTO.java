package com.infrastructure.pojo;

import lombok.AllArgsConstructor;

/**
 * 描述: 用于token验证时的用户信息传输
 * @author suifeng
 * 日期: 2024/7/5
 */
@AllArgsConstructor
public class UserInfoDTO {
    private String userId;
    private String username;
}
