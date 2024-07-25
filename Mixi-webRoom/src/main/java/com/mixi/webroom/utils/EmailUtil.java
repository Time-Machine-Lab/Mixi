package com.mixi.webroom.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private String linkTemplate;  // 链接模板内容

    @Resource
    private JavaMailSender mailSender;

    public EmailUtil() {
    }

    public void sendLink(String to, String link) {
        String subject = "这是一条邀请链接";
        sendEmail(to, linkTemplate.replace("JOIN_URL", link), subject);
    }

    public void sendEmail(String to, String text, String subject){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}