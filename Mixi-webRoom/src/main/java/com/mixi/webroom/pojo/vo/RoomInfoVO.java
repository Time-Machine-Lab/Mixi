package com.mixi.webroom.pojo.vo;

import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XiaoChun
 * @date 2024/8/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoVO {
    private String roomId;  //房间id

    private String roomName;    //房间名

    private Integer limit;  //人数上限

    private Long createTimeMillis;   //房间创建时间

    private String createId;    //创建者id
}
