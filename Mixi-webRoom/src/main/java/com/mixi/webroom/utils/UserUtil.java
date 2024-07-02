package com.mixi.webroom.utils;

import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：XiaoChun
 * @Date：2024/6/27 下午3:55
 */
@Component
public class UserUtil {

    @Resource
    RedisUtil redisUtil;
    public boolean userState(String uid){
        return Boolean.valueOf(redisUtil.getCacheObject(RedisKeyConfig.USER_STATE + uid));
    }
}
