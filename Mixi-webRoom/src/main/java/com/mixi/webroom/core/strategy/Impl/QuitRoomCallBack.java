package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.utils.RedisUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author XiaoChun
 * @date 2024/7/19
 */
@Component
public class QuitRoomCallBack extends AbstractCallBack{
    @Resource
    RedisUtil redisUtil;
    @Override
    public Boolean successCallBack(String roomId, String uid) {
        if((uid.equals(redisUtil.getCacheObject(RedisKeyConfig.userOwn(uid))))) {
            redisUtil.multi();
            redisUtil.deleteObject(RedisKeyConfig.roomInfo(roomId));
            redisUtil.deleteObject(RedisKeyConfig.roomNumber(roomId));
            redisUtil.deleteObject(RedisKeyConfig.userOwn(uid));
            redisUtil.exec();
        }
        redisUtil.deleteObject(RedisKeyConfig.userConnected(uid));
        return null;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return null;
    }
}
