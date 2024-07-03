package com.mixi.webroom.pojo.dto;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午3:34
 */
@Data
public class CreateRoomDTO {

    @NotBlank(message = "RoomName cannot be null!")
    @Length(max = 1024, message = "The longest room name is 1024.")
    private String roomName;    //房间名称

    @NotNull(message = "Limit cannot be null!")
    @Min(value = 1, message = "The minimum value is 1.")
    @Max(value =512, message = "The maximum value is 512.")
    private Integer limit;      //人数上限

    @NotNull(message = "AnonymityFlag cannot be null!")
    private Boolean anonymityFlag;  //匿名用户是否准入
}
