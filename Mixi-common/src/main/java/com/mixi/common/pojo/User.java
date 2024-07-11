package com.mixi.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName mixi_user
 */
@AllArgsConstructor
@Builder
@Data
public class User implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 账号
     */
    private String username;

    /**
     * 头像
     */
    private String password;

    /**
     * 昵称
     */
    private String avatar;

    /**
     * 密码
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 自我介绍
     */
    private String resume;

    /**
     * 注销标记
     */
    private String delFlag;
}