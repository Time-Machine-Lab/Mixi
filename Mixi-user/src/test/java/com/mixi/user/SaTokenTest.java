package com.mixi.user;

import cn.dev33.satoken.stp.StpUtil;
import com.mixi.common.component.token.service.SaTokenService;
import com.mixi.common.pojo.TokenUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 描述: SaTokenService测试类，覆盖所有接口方法
 */
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class SaTokenTest {

    @Autowired
    private SaTokenService saTokenService;

    @Test
    public void testSaTokenService() {
        TokenUserInfo tokenUserInfo = TokenUserInfo.builder()
                .userId("123456")
                .username("genius")
                .addField("email", "genius@gmail.com")
                .addField("password", "123456")
                .addField("phone", "123456")
                .addField("gender", "1")
                .build();
        String token = saTokenService.loginAndGenerateToken(tokenUserInfo);
        System.out.println(token);

        TokenUserInfo tokenUserInfo1 = saTokenService.validateAndExtractUserInfo(token);
        System.out.println(tokenUserInfo1);
    }
}