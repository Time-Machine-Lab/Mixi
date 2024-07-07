package com.mixi.user.service.impl;

import com.mixi.user.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @NAME: ThreadService
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/29
 */
@Component
@RequiredArgsConstructor
public class ThreadService {

    private final EmailUtil emailUtil;

    @Async("taskExecutor")
    public void sendEmail(String email, String link) {
        try {
            String emailTitle = "邮箱验证";
            //邮件内容
            String emailContent = "请点击链接" + link + "，完成登录，链接5分钟失效！";
            //发送邮件
            emailUtil.sendEmail(email, emailTitle, emailContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}