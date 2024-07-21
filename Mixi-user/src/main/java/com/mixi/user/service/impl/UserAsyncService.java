package com.mixi.user.service.impl;

import com.mixi.user.bean.entity.User;
import com.mixi.user.utils.EmailUtil;
import io.github.servicechain.ServiceChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserAsyncService {
    @Resource
    private EmailUtil emailUtil;

    @Resource
    private ServiceChainFactory chainFactory;

    @Async("taskExecutor")
    public void sendEmail(String to, String verifyLink, String shortLink) {
        String link = verifyLink + shortLink.replaceAll("\\+","%2B");
        log.info("向 email:{} 发送验证链接:{}",to,link);
        emailUtil.sendCode(to, link);
    }

    @Async("taskExecutor")
    public void saveUserInfo(User user){
        chainFactory.bootstrap()
                .next(chainFactory.getChain("userInfoCache"))
                .execute(user);
    }

}
