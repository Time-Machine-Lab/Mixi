package com.mixi.webroom.service.Impl;

import com.mixi.webroom.service.WebRoomRpcService;
import com.mixi.webroom.utils.RedisUtil;
import io.github.common.web.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@Service
public class WebRoomRpcServiceImpl implements WebRoomRpcService {
    @Resource
    RedisUtil redisUtil;

    /*
    * 处理回调
    * */
    @Override
    public Result<?> joinRoomCallBack(String uid, String roomId) {

        return null;
    }
}
