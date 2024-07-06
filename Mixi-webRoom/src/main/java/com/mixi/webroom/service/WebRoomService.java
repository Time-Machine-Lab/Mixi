package com.mixi.webroom.service;

import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import io.github.common.web.Result;
import org.springframework.stereotype.Component;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Component
public interface WebRoomService {

    Result createRoom(CreateRoomDTO createRoomDTO, String uid);

    Result shareRoom();

    Result joinRoom();

    Result quitRoom();

}
