package com.mixi.user.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.common.component.token.service.SaTokenService;
import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.R;
import com.mixi.common.utils.UserThread;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述: 测试的，不用管
 * @author suifeng
 * 日期: 2024/7/19
 */
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
@ApiAuth
public class LoginTestController {

    private final SaTokenService saTokenService;

    @ApiAuth(AuthType.NOT)
    @GetMapping(value = "/login")
    public R<String> login(){

        TokenUserInfo tokenUserInfo = TokenUserInfo.builder()
                .userId("123456")
                .username("genius")
                .addField("email", "genius@gmail.com")
                .addField("password", "123456")
                .addField("phone", "123456")
                .addField("gender", "1")
                .addField("age", "1")
                .roles(new int[]{1001, 1002})
                .build();


        String token = saTokenService.loginAndGenerateToken(tokenUserInfo);

        return R.success(token);
    }

    @ApiAuth(AuthType.OPTIONAL)
    @PostMapping(value = "/getUserInfo")
    public R<TokenUserInfo> testGetUserInfoFromToken(){
        TokenUserInfo tokenUserInfo = UserThread.get();
        String userId = UserThread.getUserId();
        String username = UserThread.getUsername();
        Object email = UserThread.getField("email");
        return R.success(tokenUserInfo);
    }

    @ApiAuth(roles = 1001)
    @PostMapping(value = "/testNeed1")
    public R<TokenUserInfo> testNeed1(){
        return R.success(UserThread.get());
    }

    @ApiAuth(roles = 1002)
    @PostMapping(value = "/testNeed2")
    public R<TokenUserInfo> testNeed2(){
        return R.success(UserThread.get());
    }

    @ApiAuth(roles = 1003)
    @PostMapping(value = "/testNeed3")
    public R<TokenUserInfo> testNeed3(){
        return R.success(UserThread.get());
    }
}
