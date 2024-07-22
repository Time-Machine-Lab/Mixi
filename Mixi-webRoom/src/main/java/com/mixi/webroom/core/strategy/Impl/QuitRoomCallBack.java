package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.constants.RedisKeyConstants;
import com.mixi.webroom.utils.RedisUtil;
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
        if((uid.equals(redisUtil.getCacheObject(RedisKeyConstants.userOwn(uid))))) {
            redisUtil.multi();
            redisUtil.deleteObject(RedisKeyConstants.roomInfo(roomId));
            redisUtil.deleteObject(RedisKeyConstants.roomNumber(roomId));
            redisUtil.deleteObject(RedisKeyConstants.userOwn(uid));
            redisUtil.exec();
        }
        redisUtil.deleteObject(RedisKeyConstants.userConnected(uid));
        return null;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return null;
    }
}
