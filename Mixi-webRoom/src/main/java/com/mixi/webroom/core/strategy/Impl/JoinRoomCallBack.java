package com.mixi.webroom.core.strategy.Impl;

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
//        redisUtil.removeExpiration();
//        redisUtil.removeExpiration();
//        redisUtil.removeExpiration();
        return null;
    }

    @Override
    public Boolean failCallBack(String roomId, String uid) {
        return null;
    }
}
