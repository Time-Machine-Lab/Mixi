package com.mixi.webroom.service;

import org.springframework.stereotype.Component;

/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Component
public interface WebRoomService {
    String createRoom();

    String shareRoom();

    String joinRoom();

    String quitRoom();

}
