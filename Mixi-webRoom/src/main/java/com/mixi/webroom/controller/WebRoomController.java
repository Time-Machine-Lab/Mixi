package com.mixi.webroom.controller;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.common.annotation.UserState;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.springframework.web.bind.annotation.*;
import io.github.common.web.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
*@Author：XiaoChun
*@Date：2024/6/25  17:18
*/
@RestController
@RequestMapping("/webRoom")
public class WebRoomController {

    @Resource
    WebRoomService webRoomService;

    //uid 标识用户
    @PostMapping("/create")
    @UserState(value = UserStateEnum.NORMAL)
    public Result createRoom(@RequestBody @Valid CreateRoomDTO createRoomDTO,
                             @RequestHeader @NotBlank String uid) {
        return webRoomService.createRoom(createRoomDTO, uid);
    }

    @PostMapping("/share")
    public Result shareRoom() {
        return webRoomService.shareRoom();
    }

    @PostMapping("/join")
    public Result joinRoom() {
        return webRoomService.joinRoom();
    }

    @PostMapping("/quit")
    public Result quitRoom() {
        return webRoomService.quitRoom();
    }
}
