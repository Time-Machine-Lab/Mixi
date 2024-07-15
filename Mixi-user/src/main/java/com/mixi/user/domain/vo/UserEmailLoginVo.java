package com.mixi.user.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @NAME: UserEmailLoginVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/15
 */
@Data
public class UserEmailLoginVo {
    @NotNull(message = "email 不能为空")
    private String email;
    @NotNull(message = "code 不能为空")
    private String code;
}