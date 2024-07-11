package com.mixi.webroom.service.Impl;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.core.chain.tasks.ShareTaskDO;
import com.mixi.webroom.core.worker.SnowFlakeIdWorker;
import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.core.rpc.VideoService;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.pojo.DO.WebRoom;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.*;
import io.github.common.web.Result;
import io.github.servicechain.ServiceChainFactory;
import io.github.servicechain.bootstrap.FailCallback;
import io.github.servicechain.bootstrap.ServiceChainBootstrap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private EmailUtil emailUtil;

    @Resource
    private ServiceChainFactory factory;

    @Resource
    private LuaUtil luaUtil;

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
            Map<String, Object> map = new HashMap<>();

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
        Map<String, Object> resultMap = new HashMap<>();
        if(roomId == null){
            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
        }
        String roomLink = redisUtil.getCacheObject(RedisKeyConfig.roomLink(roomId));
        resultMap.put("link", roomLink);
        return Result.success(resultMap);
//        ServiceChainBootstrap bootstrap = factory
//                .get("OwnRoomNotEmpty")
//                .failCallbackMap(Map.of(
//                        1, () -> {
//                            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
//                        }
//                ))
//                .successCallbackMap(Map.of(
//                        1, () -> {
//                            String roomLink = redisUtil.getCacheObject(RedisKeyConfig.roomLink(roomId));
//                            resultMap.put("link", roomLink);
//                        }
//                ));
//        return
    }

    @Override
    public Result<?> pull(String uid, List<String> ids) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.roomOwner(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
        }
        List<String> emailList = new ArrayList<>();//获取用户的email
        String roomLink = redisUtil.getCacheObject(RedisKeyConfig.roomLink(roomId));
//        emailUtil.sendLink(); 发送邮箱 有被打崩的风险
        return Result.success();
    }

    @Override
    public Result<?> linkJoin(String uid, String key) {
        Map<String, Object> resultMap = new HashMap<>();
        String roomId = webRoomUtil.decryptLink(key);

        userUtil.setUserState(uid, UserStateEnum.READY);
        return Result.success(resultMap);
    }

    @Override
    public Result<?> quitRoom() {
        return Result.success();
    }
}
