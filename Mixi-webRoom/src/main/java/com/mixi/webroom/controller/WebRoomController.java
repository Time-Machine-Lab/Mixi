package com.mixi.webroom.controller;

import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import org.springframework.web.bind.annotation.*;
import io.github.common.web.Result;

import javax.annotation.Resource;

/**
*@Author：XiaoChun
*@Date：2024/6/25  17:18
*/
@RestController
@RequestMapping("/webRoom")
public class WebRoomController {

    @Resource
    WebRoomService webRoomService;

    //uid ，ip和deviceId其中一个
    @PostMapping("/create")
    public Result createRoom(@RequestBody CreateRoomDTO createRoomDTO,
                             @RequestHeader String uid,
                             @RequestHeader String ip,
                             @RequestHeader String deviceId) {
        return webRoomService.createRoom(createRoomDTO);
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
