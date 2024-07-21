package com.mixi.webroom.service.Impl;

import com.mixi.webroom.core.factory.TicketFactory;
import com.mixi.webroom.core.worker.SnowFlakeIdWorker;
import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.core.rpc.VideoService;
import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.domain.entity.WebRoom;
import com.mixi.webroom.domain.dto.CreateRoomDTO;
import com.mixi.webroom.domain.vo.JoinVO;
import com.mixi.webroom.service.EmailService;
import com.mixi.webroom.service.WebRoomService;
import com.mixi.webroom.utils.*;
import io.github.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @Author：XiaoChun
 * @Date：2024/6/25 17:20
 */
@Service
@Slf4j
public class WebRoomServiceImpl implements WebRoomService {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private VideoService videoService;

    @Resource
    private SnowFlakeIdWorker snowFlakeIdWorker;

    @Resource
    private WebRoomUtil webRoomUtil;

    @Resource
    private EmailService asyncEmailSender;

    @Resource
    private TicketFactory ticketFactory;

    @Value("${netty.socket-ip}")
    private String socketIp;

    @Override
    public Result<?> createRoom(CreateRoomDTO createRoomDTO, String uid) {
        if(redisUtil.exist(RedisKeyConfig.userConnected(uid))){
            throw new ServerException(ResultEnums.USER_CONNECTED);
        }
        // 生成roomId
        String roomId = String.valueOf(snowFlakeIdWorker.nextId());
        Map<String, Object> resultMap = new HashMap<>();
        // 判断当前用户状态
        if(redisUtil.setNxObject(RedisKeyConfig.userOwn(uid), roomId, 60, TimeUnit.SECONDS)){
            WebRoom webRoom = new WebRoom(createRoomDTO, roomId);

            videoService.createRoom();
            // 设置房间相关信息
            redisUtil.setNxObject(RedisKeyConfig.roomOwner(webRoom.getRoomId()), uid, 60, TimeUnit.SECONDS);
            redisUtil.setNxObject(RedisKeyConfig.roomInfo(webRoom.getRoomId()), webRoom, 60, TimeUnit.SECONDS);
            redisUtil.setNxObject(RedisKeyConfig.roomNumber(webRoom.getRoomId()), Integer.MAX_VALUE - webRoom.getLimit(), 60, TimeUnit.SECONDS);//设置为redis上限减房间上限
        }

        String roomLink = webRoomUtil.link(WebRoomUtil.builder().put("roomId", redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid))).done());
        resultMap.put("link", roomLink);
        return Result.success(resultMap);
    }

    @Override
    public Result<?> linkShare(String uid) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid));
        Map<String, Object> resultMap = new HashMap<>();

        if(roomId == null){
            throw new ServerException(ResultEnums.THE_USER_DID_NOT_CREATE_A_ROOM);
        }

        String roomLink = webRoomUtil.link(WebRoomUtil.builder().put("roomId", roomId).done());
        resultMap.put("link", roomLink);
        return Result.success(resultMap);
    }

    @Override
    public Result<?> pull(String uid, List<String> emails) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.THE_USER_DID_NOT_CREATE_A_ROOM);
        }
        if(!redisUtil.setNxObject(RedisKeyConfig.roomPullFlag(roomId), true, 3, TimeUnit.MINUTES)){
            throw new ServerException(ResultEnums.PULL_HAS_NOT_COOLED_DOWN);
        }

        String roomLink = webRoomUtil.link(WebRoomUtil.builder().put("roomId", roomId).done());
        for(String email : new HashSet<>(emails)){
            asyncEmailSender.sendLink(email, roomLink);
        }

        return Result.success();
    }

    @Override
    public Result<?> linkJoin(String uid, String key) {
        Map<String, String> res = webRoomUtil.decryptLink(key);
        //用户当前状态为已连接
        if(!Objects.isNull(redisUtil.getCacheObject(RedisKeyConfig.userConnected(uid)))){
            throw new ServerException(ResultEnums.USER_CONNECTED);
        }

        if(!redisUtil.exist(RedisKeyConfig.roomInfo(res.get("roomId")))){
            throw new ServerException(ResultEnums.ROOM_NOT_EXIST);
        }

        try {
            redisUtil.increment(RedisKeyConfig.roomNumber(res.get("roomId")));
        } catch (Exception e){
            throw new ServerException(ResultEnums.ROOM_FULLED);
        }
        WebRoom webRoom = redisUtil.getCacheObject(RedisKeyConfig.roomInfo(res.get("roomId")));

        String ticket = ticketFactory.createTicket(TicketFactory.builder().put("uid", uid).put("roomId", res.get("roomId")).done());
        JoinVO joinVO = JoinVO.builder().videoIp(webRoom.getVideoIp()).ticket(ticket).socketIp(socketIp).build();
        redisUtil.setCacheObject(RedisKeyConfig.userTicket(uid), ticket, 60, TimeUnit.SECONDS);
        return Result.success(joinVO);
    }

    @Override
    public Result<?> quitRoom(String uid, String roomId) {
        // 房主 or 成员
        if((uid.equals(redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid))))) {
            redisUtil.deleteObject(RedisKeyConfig.roomInfo(roomId));
            redisUtil.deleteObject(RedisKeyConfig.roomNumber(roomId));
            redisUtil.deleteObject(RedisKeyConfig.roomOwner(roomId));
            redisUtil.deleteObject(RedisKeyConfig.userOwn(uid));
        }
        redisUtil.deleteObject(RedisKeyConfig.userConnected(uid));
        return Result.success();
    }
}
