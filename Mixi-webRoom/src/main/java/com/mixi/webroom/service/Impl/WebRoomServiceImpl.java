package com.mixi.webroom.service.Impl;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.common.ResultUtil;
import com.mixi.webroom.common.enums.ResultEnums;
import com.mixi.webroom.common.exception.ServerException;
import com.mixi.webroom.common.rpc.VideoService;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.pojo.DO.WebRoom;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.RedisUtil;
import com.mixi.webroom.utils.UserUtil;
import com.mixi.webroom.utils.WebRoomUtil;
import io.github.common.web.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private WebRoomUtil webRoomUtil;

    @Value("${netty.socket-ip}")
    private String socketIp;

    @Override
    public Result<?> createRoom(CreateRoomDTO createRoomDTO, String uid) {
        WebRoom webRoom = new WebRoom(createRoomDTO, uid);
        Map<String, Object> resultMap = new HashMap<>();

        if(redisUtil.setNxObject(RedisKeyConfig.roomOwner(uid), webRoom.getRoomId())){
            redisUtil.setCacheObject(RedisKeyConfig.roomInfo(webRoom.getRoomId()), webRoom);
            redisUtil.setCacheObject(RedisKeyConfig.roomLimit(webRoom.getRoomId()), webRoom.getLimit());
            resultMap.put("video", videoService.createRoom().getData());
            resultMap.put("socketIp", socketIp);
            userUtil.setUserState(uid, UserStateEnum.READY);
            return Result.success(resultMap);
        } else {
            resultMap.put("link", webRoomUtil.link(redisUtil.getCacheObject(RedisKeyConfig.WEB_ROOM + uid)));
            userUtil.setUserState(uid, UserStateEnum.READY);
            return ResultUtil.error(ResultEnums.USER_HAS_ROOM, resultMap);
        }
    }

    @Override
    public Result<?> linkShare(String uid) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.roomOwner(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
        }
        WebRoom webRoom = redisUtil.getCacheObject(RedisKeyConfig.roomInfo(roomId));
        return Result.success(webRoomUtil.link(webRoom));
    }

    @Override
    public Result<?> pull(String uid, List<String> ids) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.roomOwner(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
        }
        WebRoom webRoom = redisUtil.getCacheObject(RedisKeyConfig.roomInfo(roomId));
        webRoomUtil.link(webRoom);
        return Result.success();
    }

    @Override
    public Result<?> linkJoin(String key) {
        return Result.success();
    }

    @Override
    public Result<?> quitRoom() {
        return Result.success();
    }
}
