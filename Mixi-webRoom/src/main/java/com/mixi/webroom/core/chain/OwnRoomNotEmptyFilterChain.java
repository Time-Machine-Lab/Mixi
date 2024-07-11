package com.mixi.webroom.core.chain;

import com.mixi.webroom.config.RedisKeyConfig;
import com.mixi.webroom.core.chain.tasks.ShareTaskDO;
import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.pojo.DO.WebRoom;
import com.mixi.webroom.utils.RedisUtil;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：XiaoChun
 * @Date：2024/7/10 上午10:33
 */
@Chain("OwnRoomNotEmpty")
public class OwnRoomNotEmptyFilterChain extends AbstractFilterChain<String> {

    @Resource
    RedisUtil redisUtil;

    @Override
    public List<ServicePoint> servicePoints() {
        return List.of();
    }

    @Override
    public boolean filter(String uid) {
        String roomId = redisUtil.getCacheObject(RedisKeyConfig.roomOwner(uid));
        return roomId != null
                && roomId.isEmpty();
    }
}
