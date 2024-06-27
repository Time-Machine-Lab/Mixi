package com.mixi.webroom.controller;

import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/create")
    public Result createRoom(@RequestBody CreateRoomDTO createRoomDTO) {
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
