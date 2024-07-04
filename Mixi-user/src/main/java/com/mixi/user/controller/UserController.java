package com.mixi.user.controller;

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
        return Result.success("接口暂未开发");
    }

    @GetMapping(value = "/link")
    @SystemLog(businessName = "用户链接操作")
    public Result linkLogin(String email,String type){
        return Result.success(userService.linkLogin(email,type));
    }

    @GetMapping(value = "/link/verify")
    @SystemLog(businessName = "用户链接登录认证")
    public Result linkVerify(String email,String uid,String type){
        return Result.success(userService.linkVerify(email,uid,type));
    }


    @GetMapping(value = "/update/info")
    @SystemLog(businessName = "修改用户信息")
    public Result updateInfo(@RequestHeader String uid,
                             @RequestBody InfoVo infoVo){
        return Result.success(userService.updateInfo(uid,infoVo));
    }

//    @PostMapping(value = "/link/register")
//    @SystemLog(businessName = "用户链接注册")
//    public Result linkRegister(@RequestBody  @Valid UserLoginVo userLoginVo){
//        return Result.success(userService.linkRegister(userLoginVo));
//    }
    @PostMapping(value = "/common/register")
    @SystemLog(businessName = "用户常规注册")
    public Result commonRegister(@RequestBody  @Valid UserRegisterVo userRegisterVo){
        return Result.success(userService.commonRegister(userRegisterVo));
    }


}