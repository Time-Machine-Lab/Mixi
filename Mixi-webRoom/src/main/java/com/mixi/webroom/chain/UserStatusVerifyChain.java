package com.mixi.webroom.chain;

import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.pojo.enums.ResultEnums;
import com.mixi.webroom.domain.RedisOption;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import javax.annotation.Resource;
import java.util.List;

import static com.mixi.webroom.constants.ChainConstants.USER_STATUS_VERIFY;
import static com.mixi.webroom.constants.RedisKeyConstants.CONNECTED;
import static com.mixi.webroom.constants.RedisKeyConstants.user;


/**
 * @author XiaoChun
 * @date 2024/7/22
 */
@Chain(USER_STATUS_VERIFY)
public class UserStatusVerifyChain extends AbstractFilterChain<String> {

    @Resource
    RedisOption redisOption;

    @Override
    public List<ServicePoint> servicePoints() {
        return List.of();
    }

    @Override
    public boolean filter(String uid) {
        if(redisOption.hashExist(user(uid), CONNECTED)){
            throw new ServerException(ResultEnums.USER_CONNECTED);
        }
        return true;
    }
}
