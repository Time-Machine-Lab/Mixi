package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.config.RedisKeyConfig;
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
        if(roomId.equals(redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid)))){
            redisUtil.removeExpiration(RedisKeyConfig.roomOwner(roomId));
            redisUtil.removeExpiration(RedisKeyConfig.roomInfo(roomId));
            redisUtil.removeExpiration(RedisKeyConfig.roomNumber(roomId));
            redisUtil.removeExpiration(RedisKeyConfig.userOwn(uid));
        }
        redisUtil.setNxObject(RedisKeyConfig.userConnected(uid), roomId);
        return null;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return null;
    }
}
