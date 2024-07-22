package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.constants.RedisKeyConstants;
import com.mixi.webroom.utils.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午6:02
 */
@Component(value = "joinRoom")
public class JoinRoomCallBack extends AbstractCallBack {
    @Resource
    RedisUtil redisUtil;


    @Override
    public Boolean successCallBack(String roomId, String uid) {
        if(roomId.equals(redisUtil.getCacheObject(RedisKeyConstants.userOwn(uid)))){
            redisUtil.removeExpiration(RedisKeyConstants.roomOwner(roomId));
            redisUtil.removeExpiration(RedisKeyConstants.roomInfo(roomId));
            redisUtil.removeExpiration(RedisKeyConstants.roomNumber(roomId));
            redisUtil.removeExpiration(RedisKeyConstants.userOwn(uid));
        }
        redisUtil.setNxObject(RedisKeyConstants.userConnected(uid), roomId);
        return null;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return null;
    }
}
