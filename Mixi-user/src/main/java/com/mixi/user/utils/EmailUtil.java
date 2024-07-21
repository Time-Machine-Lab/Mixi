package com.mixi.user.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Component
public class EmailUtil {
	@Value("${spring.mail.username}")
	private String from;
	@Resource
	private String htmlContent;  // HTML模板内容
	@Resource
	private JavaMailSender mailSender;

	public EmailUtil() {
	}

	public void sendCode(String to, String verifyLink) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

		try {
			helper.setTo(to);
			helper.setFrom(from);
			// 邮件主题
			String subject = "Mixi邮箱验证";
			helper.setSubject(subject);
			helper.setText(htmlContent.replace("VerifyLink", verifyLink), true);
			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
