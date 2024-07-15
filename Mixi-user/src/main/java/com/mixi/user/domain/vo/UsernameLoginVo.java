package com.mixi.user.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @NAME: UsernameLoginVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/25
 */
@Data
//提供用户名密码或者邮箱来进行登录
public class UsernameLoginVo {
    @NotNull(message = "username 不能为空")
    private String username;
    @NotNull(message = "password 不能为空")
    private String password;
    @NotNull(message = "code 不能为空")
    private String code;
}