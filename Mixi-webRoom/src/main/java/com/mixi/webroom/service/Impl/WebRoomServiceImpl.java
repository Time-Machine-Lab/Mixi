package com.mixi.webroom.service.Impl;

import com.mixi.webroom.service.WebRoomService;
import org.springframework.stereotype.Service;

/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Service
public class WebRoomServiceImpl implements WebRoomService {
    @Override
    public String createRoom() {
        return "test";
    }

    @Override
    public String shareRoom() {
        return "";
    }

    @Override
    public String joinRoom() {
        return "";
    }

    @Override
    public String quitRoom() {
        return "";
    }
}
