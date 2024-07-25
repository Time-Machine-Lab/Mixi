package com.mixi.server.pojo.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/14 15:16
 */
@Data
public class RemoveChannelReqDTO {
    private String uid;
    private String roomId;
    private boolean owner;
}
