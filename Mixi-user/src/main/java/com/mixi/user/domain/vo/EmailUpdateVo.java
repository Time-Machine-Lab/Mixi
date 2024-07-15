package com.mixi.user.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @NAME: EmailUpdateVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/14
 */
@Data
public class EmailUpdateVo {
    @NotNull(message = "code 不能为空")
    private String code;
    @NotNull(message = "email 不能为空")
    private String email;
    @NotNull(message = "newEmail 不能为空")
    private String newEmail;
//  老邮箱还是新邮箱
    @NotNull(message = "type 不能为空")
    private String type;
}