package com.mixi.webroom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestPart;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author XiaoChun
 * @date 2024/7/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallBackDTO {
    @NotBlank
    String uid;

    @NotBlank
    String roomId;

    @NotBlank
    String callBackName;

    @NotNull
    Boolean type;
}
