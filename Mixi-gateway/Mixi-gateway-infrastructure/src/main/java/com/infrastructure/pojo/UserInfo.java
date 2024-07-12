package com.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 用户信息类，用于存储从Token中提取的用户信息
 * @author suifeng
 * 日期: 2024/7/12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfo {

    private String userId;

    private String username;

    private int[] roles;
}