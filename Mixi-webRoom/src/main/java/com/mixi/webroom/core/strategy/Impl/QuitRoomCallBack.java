package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.domain.RedisOption;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.mixi.webroom.constants.RedisKeyConstants.*;

/**
 * @author XiaoChun
 * @date 2024/7/19
 */
@Component(value = "quitRoom")
public class QuitRoomCallBack extends AbstractCallBack{
    @Resource
    RedisOption redisOption;
    @Override
    public Boolean successCallBack(String roomId, String uid) {
        //房主删除房间 用户删除自身
        if((uid.equals(redisOption.getHashString(webRoom(roomId), OWNER)))) {
            redisOption.deleteHash(webRoom(roomId));
        }
        redisOption.deleteHash(user(uid));
        return true;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return true;
    }
}
