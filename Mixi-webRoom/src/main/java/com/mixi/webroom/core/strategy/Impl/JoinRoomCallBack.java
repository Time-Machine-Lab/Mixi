package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.domain.RedisOption;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.mixi.webroom.constants.RedisKeyConstants.*;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午6:02
 */
@Component(value = "joinRoom")
public class JoinRoomCallBack extends AbstractCallBack {
    @Resource
    RedisOption redisOption;


    @Override
    public Boolean successCallBack(String roomId, String uid) {
        if((uid.equals(redisOption.getHashString(webRoom(roomId), OWNER)))) {
            redisOption.removeExpiration(webRoom(roomId));
        }
        redisOption.setHashNx(user(uid), CONNECTED, roomId);
        return true;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return true;
    }
}
