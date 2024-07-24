package com.mixi.user.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TouristLoginDTO extends LoginDTO {

    @NotBlank(message = "指纹不能为空")
    private String fingerprint;
}
