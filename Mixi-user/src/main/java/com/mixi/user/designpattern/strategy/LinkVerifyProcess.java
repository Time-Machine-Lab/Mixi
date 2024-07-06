package com.mixi.user.designpattern.strategy;

import com.mixi.user.designpattern.strategy.imp.LoginLinkVerifyStrategy;
import com.mixi.user.designpattern.strategy.imp.RegisterLinkVerifyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @NAME: LinkVerifyProcess
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/6
 */
@Component
public class LinkVerifyProcess {
    private final Map<String, LinkVerifyStrategy> strategyMap = new HashMap<>();

    @Autowired
    public LinkVerifyProcess(LoginLinkVerifyStrategy loginLinkVerifyStrategy, RegisterLinkVerifyStrategy registerLinkVerifyStrategy) {
        strategyMap.put("1" ,loginLinkVerifyStrategy);
        strategyMap.put("2",registerLinkVerifyStrategy);
    }

    public String process(String type,String email,String uid){
        String token = strategyMap.get(type).process(email, uid);
        return token;
    }
}