package com.mixi.user.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private String id;

    private String username;

    private String avatar;

    private String nickname;

    private String sex;

    private String resume;

}
