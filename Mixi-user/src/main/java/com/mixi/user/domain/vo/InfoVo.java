package com.mixi.user.domain.vo;

import lombok.Data;

/**
 * @NAME: InfoVo
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/4
 */
@Data
public class InfoVo {
    private String id;
//    不可修改
    private String username;
//满足条件修改
    private String email;
    private String password;
//    随意修改
    private String avatar;
    private String nickname;
    private String sex;
    private String resume;
}