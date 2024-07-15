package com.mixi.webroom.service;

import com.mixi.webroom.domain.dto.CreateRoomDTO;
import io.github.common.web.Result;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Component
public interface WebRoomService {
    Result<?> createRoom(CreateRoomDTO createRoomDTO, String uid);

    Result<?> linkShare(String uid);

    Result<?> pull(String uid, List<String> emails);

    Result<?> linkJoin(String uid, String key);

    Result<?> quitRoom(String uid, String roomId);

}
