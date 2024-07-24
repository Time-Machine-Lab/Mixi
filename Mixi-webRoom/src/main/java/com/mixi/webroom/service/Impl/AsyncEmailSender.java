package com.mixi.webroom.service.Impl;

import com.mixi.webroom.service.EmailService;
import com.mixi.webroom.utils.EmailUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AsyncEmailSender implements EmailService {
    @Resource
    private EmailUtil emailUtil;

    @Async("asyncEmailExecutor")
    @Override
    public void sendLink(String to, String link) {
        emailUtil.sendLink(to, link);
    }
}