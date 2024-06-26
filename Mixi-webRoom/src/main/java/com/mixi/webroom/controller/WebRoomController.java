package com.mixi.webroom.controller;

import com.mixi.webroom.service.WebRoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String createRoom() {
        return webRoomService.createRoom();
    }

    @PostMapping("/share")
    public String shareRoom() {
        return webRoomService.shareRoom();
    }

    @PostMapping("/join")
    public String joinRoom() {
        return webRoomService.joinRoom();
    }

    @PostMapping("/quit")
    public String quitRoom() {
        return webRoomService.quitRoom();
    }
}
