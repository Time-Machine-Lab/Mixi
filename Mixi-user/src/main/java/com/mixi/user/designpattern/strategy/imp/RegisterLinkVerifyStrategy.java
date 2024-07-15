package com.mixi.user.designpattern.strategy.imp;

import com.mixi.user.designpattern.chain.ApproveChainBuilder;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategy;
import com.mixi.user.designpattern.strategy.LinkVerifyStrategyFactory;
import com.mixi.user.domain.entity.User;
import com.mixi.user.service.impl.RedisDaoService;
import com.mixi.user.service.impl.UserDaoService;
import com.mixi.user.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.mixi.user.constants.RedisConstant.REDIS_PRE;

/**
 * @NAME: RegisterLinkVerifyStrategy
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/6
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterLinkVerifyStrategy implements LinkVerifyStrategy {

    private final String NAME = "RegisterLinkVerifyStrategy";
    private final RedisDaoService redisDaoService;
    private final UserDaoService userDaoService;
    private final ApproveChainBuilder approveChainBuilder;
    @Override
    public String process(String email, String uid) {
        approveChainBuilder.buildInstance()
                .set("RedisQueryExistCheck", REDIS_PRE + email + ":" + "uid", uid)
                .Build()
                .approve();
        redisDaoService.deleteKeys(REDIS_PRE + email + ":" + "uid",REDIS_PRE + email + ":" + "times");
        User user = User.baseBuild(email);
        userDaoService.insert(user);
        return TokenUtil.getToken(user.getId(),email);
    }


    //todo 向外抽象 模板方法
    @Override
    public void afterPropertiesSet() throws Exception {
        LinkVerifyStrategyFactory.register(NAME,this);
    }
}