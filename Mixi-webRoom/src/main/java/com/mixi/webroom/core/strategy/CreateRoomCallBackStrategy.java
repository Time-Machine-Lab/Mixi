package com.mixi.webroom.core.strategy;

import com.mixi.webroom.utils.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午6:02
 */
@Component
public class CreateRoomCallBackStrategy extends AbstractCallBackStrategy{

    @Resource
    RedisUtil redisUtil;

    @Override
    public Boolean successCallBack() {
//        redisUtil.
        return null;
    }

    @Override
    public Boolean failCallBack() {
        return null;
    }
}
