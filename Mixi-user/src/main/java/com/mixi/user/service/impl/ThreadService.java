package com.mixi.user.service.impl;

import com.mixi.user.utils.EmailUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @NAME: ThreadService
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/29
 */
@Component
public class ThreadService {
    @Async("taskExecutor")
    public void sendEmail(String email, String link) {
        try {
            EmailUtil.send(email,link);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}