package com.mixi.webroom.pojo.dto;

import lombok.Data;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午3:34
 */
@Data
public class CreateRoomDTO {
    private String deviceId;  //设备名

    private String ip;  //ip 地址

    private String uid; //用户id
}
