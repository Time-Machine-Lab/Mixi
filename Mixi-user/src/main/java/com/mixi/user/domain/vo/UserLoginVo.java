package com.mixi.user.domain.vo;

import lombok.Data;

/**
 * @NAME: UserLoginVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/25
 */
@Data
//提供用户名密码或者邮箱来进行登录
public class UserLoginVo {
    private String username;
    private String password;

    private String email;
}