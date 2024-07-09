package com.mixi.webroom.service.Impl;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.core.worker.SnowFlakeIdWorker;
import com.mixi.webroom.utils.ResultUtil;
import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.core.rpc.VideoService;
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
    SnowFlakeIdWorker snowFlakeIdWorker;

    @Resource
    private UserUtil userUtil;

    @Resource
    private WebRoomUtil webRoomUtil;

    @Value("${netty.socket-ip}")
    private String socketIp;

    @Override
    public Result<?> createRoom(CreateRoomDTO createRoomDTO, String uid) {
        String roomId = String.valueOf(snowFlakeIdWorker.nextId());
        Map<String, Object> resultMap = new HashMap<>();
        String roomLink = null;
        // 判断当前用户状态
        if(redisUtil.setNxObject(RedisKeyConfig.userOwn(uid), roomId)){
            WebRoom webRoom = new WebRoom(createRoomDTO, roomId);
            roomLink = webRoomUtil.link(webRoom);
            videoService.createRoom().getData();
            redisUtil.setCacheObject(RedisKeyConfig.roomOwner(webRoom.getRoomId()), uid);
            redisUtil.setCacheObject(RedisKeyConfig.roomInfo(webRoom.getRoomId()), webRoom);
            redisUtil.setCacheObject(RedisKeyConfig.roomNumber(webRoom.getRoomId()), Integer.MAX_VALUE - webRoom.getLimit());
            redisUtil.setCacheObject(RedisKeyConfig.roomLink(webRoom.getRoomId()), roomLink);
        } else {
            roomLink = redisUtil.getCacheObject(RedisKeyConfig.roomLink(
                    redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid))
            ));
        }
        resultMap.put("link", roomLink);
        return Result.success(resultMap);
    }

    @Override
    public Result<?> linkShare(String uid) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.roomOwner(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
        }
        String roomLink = redisUtil.getCacheObject(RedisKeyConfig.roomLink(
                redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid))
        ));
        return Result.success();
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
