package com.mixi.webroom.service.Impl;

import com.mixi.webroom.common.ResultUtil;
import com.mixi.webroom.common.enums.ResultEnums;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.UserUtil;
import io.github.common.web.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Service
public class WebRoomServiceImpl implements WebRoomService {
    @Resource
    StringRedisTemplate redisTemplate;

    @Resource
    UserUtil userUtil;

    @Override
    public Result createRoom(CreateRoomDTO createRoomDTO) {
        //1、用户状态校验 判断目前用户是否在房间内 在房间内则返回错误
        if(!userUtil.userState(createRoomDTO)){
            return ResultUtil.error(ResultEnums.USER_IN_ROOM);
        }
        //2、

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
