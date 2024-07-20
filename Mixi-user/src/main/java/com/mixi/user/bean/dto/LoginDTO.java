package com.mixi.user.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Email
    private String email;

    @NotBlank(message = "图片验证码id不能为空")
    private String picId;

    @Size(min = 5, max = 5, message = "图片验证码长度必须为5")
    private String picCode;
}
