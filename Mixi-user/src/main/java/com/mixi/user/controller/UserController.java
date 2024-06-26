package com.mixi.user.controller;

import com.mixi.user.aspect.annotation.SystemLog;
import com.mixi.user.domain.vo.UserLoginVo;
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

    @PostMapping(value = "/link/login")
    @SystemLog(businessName = "用户链接登录")
    public Result linkLogin(@RequestBody  @Valid UserLoginVo userLoginVo){
        return Result.success(userService.linkLogin(userLoginVo));
    }



}