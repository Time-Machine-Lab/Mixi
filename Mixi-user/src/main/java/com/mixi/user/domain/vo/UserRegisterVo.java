package com.mixi.user.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @NAME: UserRegisterVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/4
 */
@Data
public class UserRegisterVo {
    @NotNull(message = "email 不能为空")
    private String email;
    @NotNull(message = "code 不能为空")
    private String code;
    @NotNull(message = "password 不能为空")
    private String password;
}