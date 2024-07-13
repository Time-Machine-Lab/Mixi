package com.mixi.webroom.utils;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.config.RedisKeyConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午3:55
 */
@Component
public class UserUtil {
    @Resource
    private RedisUtil redisUtil;

    public boolean setUserState(String uid, UserStateEnum userStateEnum){
        return redisUtil.setNnObject(RedisKeyConfig.userNettyState(uid), userStateEnum.getUserState()) &
                redisUtil.setNnObject(RedisKeyConfig.userVideoState(uid), userStateEnum.getUserState());
    }
}