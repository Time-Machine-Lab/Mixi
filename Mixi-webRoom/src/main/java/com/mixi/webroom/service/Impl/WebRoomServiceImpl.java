package com.mixi.webroom.service.Impl;

import com.mixi.webroom.core.factory.TicketFactory;
import com.mixi.webroom.core.worker.SnowFlakeIdWorker;
import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.core.rpc.VideoService;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.domain.entity.WebRoom;
import com.mixi.webroom.domain.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.*;
import io.github.common.web.Result;
import io.github.id.snowflake.RedisSnowflakeRegister;
import io.github.servicechain.chain.AbstractFilterChain;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
    private WebRoomUtil webRoomUtil;

    @Resource
    private EmailUtil emailUtil;

    @Resource
    private TicketFactory ticketFactory;

    @Value("${netty.socket-ip}")
    private String socketIp;

    @Override
    public Result<?> createRoom(CreateRoomDTO createRoomDTO, String uid) {
        // 生成roomId
        String roomId = String.valueOf(snowFlakeIdWorker.nextId());
        Map<String, Object> resultMap = new HashMap<>();
        String roomLink = null;
        // 判断当前用户状态
        if(redisUtil.setNxObject(RedisKeyConfig.userOwn(uid), roomId)){
            WebRoom webRoom = new WebRoom(createRoomDTO, roomId);
            roomLink = webRoomUtil.link(webRoom);

            videoService.createRoom();
            // 设置房间相关信息
            redisUtil.setCacheObject(RedisKeyConfig.roomOwner(webRoom.getRoomId()), uid, 60, TimeUnit.SECONDS);
            redisUtil.setCacheObject(RedisKeyConfig.roomInfo(webRoom.getRoomId()), webRoom, 60, TimeUnit.SECONDS);
            redisUtil.setCacheObject(RedisKeyConfig.roomNumber(webRoom.getRoomId()), Integer.MAX_VALUE - webRoom.getLimit(), 60, TimeUnit.SECONDS);//设置为redis上限减房间上限
            redisUtil.setCacheObject(RedisKeyConfig.roomLink(webRoom.getRoomId()), roomLink, 60, TimeUnit.SECONDS);
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
    }

    @Override
    public Result<?> pull(String uid, List<String> ids) {
        //待商议
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.roomOwner(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.ONLY_HOMEOWNER);
        }
        List<String> emailList = new ArrayList<>();//获取用户的email
        String roomLink = redisUtil.getCacheObject(RedisKeyConfig.roomLink(roomId));
        for(String email : emailList){
            emailUtil.sendLink(email, roomLink); //发送邮箱 有被打崩的风险
        }
        return Result.success();
    }

    @Override
    public Result<?> linkJoin(String uid, String key) {
        Map<String, Object> resultMap = new HashMap<>();
        String roomId = webRoomUtil.decryptLink(key);
        try {
            redisUtil.increment(RedisKeyConfig.roomNumber(roomId));
        } catch (Exception e){
            throw new ServerException(ResultEnums.ROOM_FULLED);
        }
        WebRoom webRoom = redisUtil.getCacheObject(RedisKeyConfig.roomInfo(roomId));
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("roomId", roomId);
        String ticket = ticketFactory.createTicket(map);
        resultMap.put("videoIp", webRoom.getVideoIp());
        resultMap.put("socketIp", socketIp);
        resultMap.put("ticket", ticket);
        redisUtil.setCacheObject(RedisKeyConfig.userTicket(uid), ticket, 60, TimeUnit.SECONDS);
        return Result.success(resultMap);
    }

    @Override
    public Result<?> quitRoom(String uid, String roomId) {
        try {
            // 房主 or 成员
            if(uid.equals(redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid)))) {
                // 删除房间
                redisUtil.deleteObject(RedisKeyConfig.roomInfo(roomId));
                redisUtil.deleteObject(RedisKeyConfig.roomLink(roomId));
                redisUtil.deleteObject(RedisKeyConfig.roomNumber(roomId));
                // 删除用户拥有房间和当前连接房间
                redisUtil.deleteObject(RedisKeyConfig.userOwn(uid));
            }
            redisUtil.deleteObject(RedisKeyConfig.userConnected(uid));
        } catch (Exception e) {
            throw new ServerException(ResultEnums.QUIT_ROOM_ERROR);
        }
        return Result.success();
    }
}
