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

import static org.junit.Assert.*;

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
        // 测试登录并生成Token
        System.out.println("测试登录并生成Token");
        TokenUserInfo userInfo = new TokenUserInfo("user123", "testUser", new int[]{1, 2, 3});
        String token = saTokenService.loginAndGenerateToken(userInfo);
        System.out.println("生成的Token: " + token);
        assertNotNull("Token不应为null", token);

        // 测试Token是否合法
        System.out.println("测试Token是否合法");
        boolean isValid = saTokenService.isTokenValid(token);
        System.out.println("Token是否有效: " + isValid);
        assertTrue("Token应为有效", isValid);

        // 测试从Token中提取用户信息
        System.out.println("测试从Token中提取用户信息");
        TokenUserInfo extractedUserInfo = saTokenService.extractUserInfoFromToken(token);
        System.out.println("提取的用户信息: " + extractedUserInfo);
        assertEquals("用户ID应匹配", userInfo.getUserId(), extractedUserInfo.getUserId());
        assertEquals("用户名应匹配", userInfo.getUsername(), extractedUserInfo.getUsername());
        assertArrayEquals("角色应匹配", userInfo.getRoles(), extractedUserInfo.getRoles());

        // 测试使用Token登出
        System.out.println("测试使用Token登出");
        saTokenService.logoutByToken(token);
        boolean isLoggedOutByToken = StpUtil.getLoginIdByToken(token) == null;
        System.out.println("是否已通过Token登出: " + isLoggedOutByToken);
        assertTrue("用户应已通过Token登出", isLoggedOutByToken);

        // 测试使用用户ID登出
        System.out.println("测试使用用户ID登出");
        token = saTokenService.loginAndGenerateToken(userInfo);
        saTokenService.logoutById(userInfo.getUserId());
        boolean isLoggedOutById = StpUtil.getLoginIdByToken(token) == null;
        System.out.println("是否已通过用户ID登出: " + isLoggedOutById);
        assertTrue("用户应已通过用户ID登出", isLoggedOutById);

        // 测试无效Token
        System.out.println("测试无效Token");
        String invalidToken = "invalidToken";
        boolean isInvalidTokenValid = saTokenService.isTokenValid(invalidToken);
        System.out.println("无效Token是否有效: " + isInvalidTokenValid);
        assertFalse("无效Token不应为有效", isInvalidTokenValid);

        // 测试从无效Token中提取用户信息
        System.out.println("测试从无效Token中提取用户信息");
        TokenUserInfo extractedUserInfoFromInvalidToken = saTokenService.extractUserInfoFromToken(invalidToken);
        System.out.println("从无效Token中提取的用户信息: " + extractedUserInfoFromInvalidToken);
        assertNull("从无效Token中提取的用户信息应为null", extractedUserInfoFromInvalidToken);
    }
}