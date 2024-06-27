package com.mixi.webroom.service;

import io.github.common.web.Result;
import org.springframework.stereotype.Component;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Component
public interface WebRoomService {
    Result createRoom();

    Result shareRoom();

    Result joinRoom();

    Result quitRoom();

}
