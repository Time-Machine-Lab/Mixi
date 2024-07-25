package com.mixi.webroom.service;

import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    /**
     * 执行异步任务
     */
    void sendLink(String to, String link);
}