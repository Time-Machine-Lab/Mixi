package com.mixi.webroom.pojo.DO;

import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author：XiaoChun
 * @Date：2024/6/28 下午5:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebRoom implements Serializable {
    private String roomId;  //房间id

    private String roomName;    //房间名

    private Integer limit;  //人数上限

    private Boolean anonymityFlag;  //匿名用户是否准入标识

    private LocalDateTime createTime;   //房间创建时间

//    private String createId;    //创建者id

    public WebRoom(CreateRoomDTO createRoomDTO) {
        this.roomId = UUID.randomUUID().toString(); //重复问题 编写自动生成注解 在写入redis时自动生成uuid并判断查重
        this.createTime = LocalDateTime.now();
        this.roomName = createRoomDTO.getRoomName();
        this.limit = createRoomDTO.getLimit();
        this.anonymityFlag = createRoomDTO.getAnonymityFlag();
    }
}
