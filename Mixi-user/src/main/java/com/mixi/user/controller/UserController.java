package com.mixi.user.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.user.aspect.annotation.SystemLog;
import com.mixi.user.domain.vo.InfoVo;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.domain.vo.UserRegisterVo;
import com.mixi.user.service.UserService;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @NAME: UserController
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/25
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@SuppressWarnings({"all"})
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/common/login")
    @SystemLog(businessName = "用户常规登录")
    public Result commonLogin(@RequestBody  @Valid UserLoginVo userLoginVo){
        return userService.login(userLoginVo);
    }

    @GetMapping(value = "/link")
    @SystemLog(businessName = "用户链接操作")
    public Result link(String email,String type){
        return userService.link(email,type);
    }

    @GetMapping(value = "/link/verify")
    @SystemLog(businessName = "用户链接认证")
    public Result linkVerify(String email,String uid,String type){
        return userService.linkVerify(email,uid,type);
    }

    @PostMapping(value = "/update/info")
    @SystemLog(businessName = "修改用户信息")
    public Result updateInfo(@RequestHeader String uid,
                             @RequestBody InfoVo infoVo){
        return userService.updateInfo(uid,infoVo);
    }
    @PostMapping(value = "/common/register")
    @SystemLog(businessName = "用户常规注册")
    public Result commonRegister(@RequestBody  @Valid UserRegisterVo userRegisterVo){
        return userService.commonRegister(userRegisterVo);
    }

    @GetMapping()
    public Result info(){
        return userService.userInfo();
    }
}