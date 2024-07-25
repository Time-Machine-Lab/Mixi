package com.mixi.webroom.pojo.entity;

import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author：XiaoChun
 * @Date：2024/6/28 下午5:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebRoom {
    private String roomId;  //房间id

    private String roomName;    //房间名

    private Integer limit;  //人数上限

    private Boolean anonymityFlag;  //匿名用户是否准入标识

    private Long createTimeMillis;   //房间创建时间

    private String socketIp;

    private String videoIp;

//    private String createId;    //创建者id

    public WebRoom(CreateRoomDTO createRoomDTO, String roomId, String socketIp, String videoIp) {
        this.roomId = roomId;
        this.createTimeMillis = System.currentTimeMillis();
        this.roomName = createRoomDTO.getRoomName();
        this.limit = createRoomDTO.getLimit();
        this.anonymityFlag = createRoomDTO.getAnonymityFlag();
    }
}
