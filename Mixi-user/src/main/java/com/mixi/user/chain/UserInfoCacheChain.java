package com.mixi.user.chain;

import com.alibaba.fastjson.JSONObject;
import com.mixi.user.bean.entity.User;
import com.mixi.user.bean.vo.UserVO;
import com.mixi.user.domain.RedisGateway;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.mixi.user.constants.ChainConstant.USER_INFO_CACHE;
import static com.mixi.user.constants.RedisKeyConstant.USER_TOKEN_KEY;

@Chain(USER_INFO_CACHE)
public class UserInfoCacheChain  extends AbstractFilterChain<User> {

    @Resource
    private RedisGateway redisGateway;

    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        // 缓存用户信息
        redisGateway.setIfAbsent(USER_TOKEN_KEY, JSONObject.toJSONString(userVO),user.getId());
        return true;
    }
}
