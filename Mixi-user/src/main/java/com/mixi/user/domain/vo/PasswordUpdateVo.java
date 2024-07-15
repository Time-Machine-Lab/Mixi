package com.mixi.user.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @NAME: PasswordUpdateVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/14
 */
@Data
public class PasswordUpdateVo {
    @NotNull(message = "email 不能为空")
    private String email;
    @NotNull(message = "oldPassword 不能为空")
    private String oldPassword;
    @NotNull(message = "newPassword 不能为空")
    private String newPassword;
}