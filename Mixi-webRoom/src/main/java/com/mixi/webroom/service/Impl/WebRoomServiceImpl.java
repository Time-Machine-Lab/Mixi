package com.mixi.webroom.service.Impl;

import com.mixi.webroom.service.WebRoomService;
import io.github.common.web.Result;
import org.springframework.stereotype.Service;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Service
public class WebRoomServiceImpl implements WebRoomService {
    @Override
    public Result createRoom() {
//        1、用户状态校验 判断目前用户是否在

        return Result.success();
    }

    @Override
    public Result shareRoom() {
        return Result.success();
    }

    @Override
    public Result joinRoom() {
        return Result.success();
    }

    @Override
    public Result quitRoom() {
        return Result.success();
    }
}
