package com.mixi.webroom.core.strategy;

import com.mixi.webroom.utils.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午6:02
 */
@Component(value = "joinRoom")
public class JoinRoomCallBack extends AbstractCallBack{
    @Resource
    RedisUtil redisUtil;


    @Override
    public Boolean successCallBack(String roomId, String uid) {
        redisUtil.multi();
//        redisUtil.removeExpiration();
//        redisUtil.removeExpiration();
//        redisUtil.removeExpiration();
        redisUtil.exec();
        return null;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return null;
    }
}
