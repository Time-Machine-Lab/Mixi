package com.mixi.webroom.service.Impl;

import com.mixi.common.factory.TicketFactory;
import com.mixi.webroom.core.worker.SnowFlakeIdWorker;
import com.mixi.webroom.pojo.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.core.rpc.VideoService;
import com.mixi.webroom.constants.RedisKeyConstants;
import com.mixi.webroom.pojo.entity.WebRoom;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.pojo.vo.JoinVO;
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
        if(redisUtil.exist(RedisKeyConstants.userConnected(uid))){
            throw new ServerException(ResultEnums.USER_CONNECTED);
        }
        // 生成roomId
        String roomId = String.valueOf(snowFlakeIdWorker.nextId());
        Map<String, Object> resultMap = new HashMap<>();
        // 判断当前用户状态
        if(redisUtil.setNxObject(RedisKeyConstants.userOwn(uid), roomId, 60, TimeUnit.SECONDS)){
            WebRoom webRoom = new WebRoom(createRoomDTO, roomId);

            videoService.createRoom();
            // 设置房间相关信息
            redisUtil.setNxObject(RedisKeyConstants.roomOwner(webRoom.getRoomId()), uid, 60, TimeUnit.SECONDS);
            redisUtil.setNxObject(RedisKeyConstants.roomInfo(webRoom.getRoomId()), webRoom, 60, TimeUnit.SECONDS);
            redisUtil.setNxObject(RedisKeyConstants.roomNumber(webRoom.getRoomId()), Integer.MAX_VALUE - webRoom.getLimit(), 60, TimeUnit.SECONDS);//设置为redis上限减房间上限
        }

        String roomLink = webRoomUtil.link(WebRoomUtil.builder().put("roomId", redisUtil.getCacheObject(RedisKeyConstants.userOwn(uid))).done());
        resultMap.put("link", roomLink);
        return Result.success(resultMap);
    }

    @Override
    public Result<?> linkShare(String uid) {
        String roomId = redisUtil.getCacheObject(RedisKeyConstants.userOwn(uid));
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
        String roomId = redisUtil.getCacheObject(RedisKeyConstants.userOwn(uid));
        if(roomId == null){
            throw new ServerException(ResultEnums.THE_USER_DID_NOT_CREATE_A_ROOM);
        }
        if(!redisUtil.setNxObject(RedisKeyConstants.roomPullFlag(roomId), true, 3, TimeUnit.MINUTES)){
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
        if(!Objects.isNull(redisUtil.getCacheObject(RedisKeyConstants.userConnected(uid)))){
            throw new ServerException(ResultEnums.USER_CONNECTED);
        }

        if(!redisUtil.exist(RedisKeyConstants.roomInfo(res.get("roomId")))){
            throw new ServerException(ResultEnums.ROOM_NOT_EXIST);
        }

        try {
            redisUtil.increment(RedisKeyConstants.roomNumber(res.get("roomId")));
        } catch (Exception e){
            throw new ServerException(ResultEnums.ROOM_FULLED);
        }
        WebRoom webRoom = redisUtil.getCacheObject(RedisKeyConstants.roomInfo(res.get("roomId")));

        String ticket = ticketFactory.createTicket(TicketFactory.builder().put("uid", uid).put("roomId", res.get("roomId")).done());
        JoinVO joinVO = JoinVO.builder().videoIp(webRoom.getVideoIp()).ticket(ticket).socketIp(socketIp).build();
        redisUtil.setCacheObject(RedisKeyConstants.userTicket(uid), ticket, 60, TimeUnit.SECONDS);
        return Result.success(joinVO);
    }

    @Override
    public Result<?> quitRoom(String uid, String roomId) {
        // 房主 or 成员
        if((uid.equals(redisUtil.getCacheObject(RedisKeyConstants.userOwn(uid))))) {
            redisUtil.deleteObject(RedisKeyConstants.roomInfo(roomId));
            redisUtil.deleteObject(RedisKeyConstants.roomNumber(roomId));
            redisUtil.deleteObject(RedisKeyConstants.roomOwner(roomId));
            redisUtil.deleteObject(RedisKeyConstants.userOwn(uid));
        }
        redisUtil.deleteObject(RedisKeyConstants.userConnected(uid));
        return Result.success();
    }
}
