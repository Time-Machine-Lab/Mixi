package com.mixi.user.domain.vo;

import lombok.Data;

/**
 * @NAME: UserRegisterVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/4
 */
@Data
public class UserRegisterVo {
    private String email;
    private String code;
    private String password;
}