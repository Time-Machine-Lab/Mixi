package com.mixi.webroom.service.Impl;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.common.ResultUtil;
import com.mixi.webroom.common.enums.ResultEnums;
import com.mixi.webroom.common.rpc.VideoService;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.pojo.DO.WebRoom;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.RedisUtil;
import com.mixi.webroom.utils.UserUtil;
import io.github.common.web.Result;
import org.springframework.beans.factory.annotation.Value;
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
    private RedisUtil redisUtil;

    @Resource
    private VideoService videoService;

    @Resource
    private UserUtil userUtil;

    @Value("${netty.socket-ip}")
    private String socketIp;

    @Override
    public Result createRoom(CreateRoomDTO createRoomDTO, String uid) {
        WebRoom webRoom = new WebRoom(createRoomDTO);
        Map<String, Object> resultMap = new HashMap<>();

        //调用redis原子性操作 如果目前有房间则返回房间
        if(redisUtil.setNxObject(RedisKeyConfig.WEB_ROOM + uid, webRoom)){
            // rpc 接口创建音视频流房间
            resultMap.put("video", videoService.createRoom().getData());
            resultMap.put("socketIp", socketIp);
            //设置状态
            userUtil.setUserState(uid, UserStateEnum.READY);

            return Result.success(resultMap);
        } else {
            //暂时未编写加入房间连接
//            resultMap.put("", ((WebRoom)redisUtil.getCacheObject(RedisKeyConfig.WEB_ROOM + uid)).getRoomId());
            userUtil.setUserState(uid, UserStateEnum.READY);
            return ResultUtil.error(ResultEnums.USER_HAS_ROOM, resultMap);
        }
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
