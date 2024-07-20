package com.mixi.user.service.impl;

import com.mixi.user.utils.EmailUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAsyncService {
    @Resource
    private EmailUtil emailUtil;

    @Async("taskExecutor")
    public void sendEmail(String to, String shortLink) {
        emailUtil.sendCode(to, shortLink);
    }

}
