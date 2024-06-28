package com.mixi.webroom.pojo.dto;

import lombok.Data;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午3:34
 */
@Data
public class CreateRoomDTO {
    private String roomName;    //房间名称

    private Integer limit;      //人数上限

    private Boolean anonymityFlag;  //匿名用户是否准入
}
