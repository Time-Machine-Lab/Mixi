package com.mixi.webroom.service.Impl;

import com.mixi.webroom.common.ResultUtil;
import com.mixi.webroom.common.enums.ResultEnums;
import com.mixi.webroom.common.rpc.NettyService;
import com.mixi.webroom.common.rpc.VideoService;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.pojo.DO.WebRoom;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.RedisUtil;
import com.mixi.webroom.utils.UserUtil;
import io.github.common.web.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Service
public class WebRoomServiceImpl implements WebRoomService {
    @Resource
    RedisUtil redisUtil;

    @Resource
    UserUtil userUtil;

    @Resource
    VideoService videoService;

    @Resource
    NettyService nettyService;

    @Override
    public Result createRoom(CreateRoomDTO createRoomDTO, String uid) {
        //1、用户状态校验 判断目前用户是否在房间内 在房间内则返回错误
        if(userUtil.userState(uid)){
            return ResultUtil.error(ResultEnums.USER_IN_ROOM);
        }
        //2、调用rpc接口创建音视频房间和心跳房间
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("video", videoService.createRoom().getData());
        resultMap.put("netty", nettyService.createRoom().getData());
        //3、保存房间信息和用户状态到redis
        WebRoom webRoom = new WebRoom(createRoomDTO);

        redisUtil.setCacheObject(RedisKeyConfig.WEB_ROOM + webRoom.getRoomId(), webRoom);

        return Result.success(resultMap);
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
