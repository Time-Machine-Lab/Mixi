package com.mixi.webroom.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.common.annotation.UserState;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import io.github.common.web.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
*@Author：XiaoChun
*@Date：2024/6/25  17:18
*/
@RestController
@RequestMapping("/webRoom")
@ApiAuth
public class WebRoomTestController {

    @Resource
    WebRoomService webRoomService;

    //uid 标识用户
    @PostMapping("/create1")
    @UserState(value = UserStateEnum.NORMAL)
    public Result createRoom(@RequestBody @Valid CreateRoomDTO createRoomDTO,
                             @RequestHeader @NotBlank String uid) {
        return webRoomService.createRoom(createRoomDTO, uid);
    }

    @PostMapping("/share1")
    public Result shareRoom() {
        return webRoomService.shareRoom();
    }

    @PostMapping("/join1")
    public Result joinRoom() {
        return webRoomService.joinRoom();
    }

    @PostMapping("/quit1")
    public Result quitRoom() {
        return webRoomService.quitRoom();
    }

    @GetMapping("/test")
    public String test() {
        return "123123";
    }
}
