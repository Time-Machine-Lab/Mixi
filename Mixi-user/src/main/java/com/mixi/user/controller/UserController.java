package com.mixi.user.controller;

import com.mixi.user.aspect.annotation.SystemLog;
import com.mixi.user.domain.vo.*;
import com.mixi.user.service.UserService;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    public Result commonLogin(@RequestBody @Valid UsernameLoginVo userLoginVo) {
        return userService.login(userLoginVo);
    }

    @GetMapping(value = "/link")
    @SystemLog(businessName = "用户链接操作")
    public Result link(@RequestParam
                       @Valid
                       @NotNull(message = "email 不能为空")
                       @Length(min = 6, max = 30, message = "邮箱长度必须在6到30之间")
                       String email,
                       @RequestParam
                       @Valid
                       @NotNull(message = "type 不能为空") String type) {
        return userService.link(email, type);
    }

    @GetMapping(value = "/link/verify")
    @SystemLog(businessName = "用户链接认证")
    public Result linkVerify(
            @RequestParam
            @Valid
            @NotNull(message = "email 不能为空") String email,
            @Valid
            @NotNull(message = "type 不能为空") String type,
            @Valid
            @NotNull(message = "uid 不能为空") String uid) {
        return userService.linkVerify(email, uid, type);
    }

    @PostMapping(value = "/update/info")
    @SystemLog(businessName = "修改用户信息")
    public Result updateInfo(@RequestHeader String uid,
                             @RequestBody InfoVo infoVo) {
        return userService.updateInfo(uid, infoVo);
    }

    @PostMapping(value = "/common/register")
    @SystemLog(businessName = "用户常规注册")
    public Result commonRegister(@RequestBody @Valid UserRegisterVo userRegisterVo) {
        return userService.commonRegister(userRegisterVo);
    }

    @GetMapping("/info")
    @SystemLog(businessName = "获取用户信息")
    public Result info(@RequestHeader String uid) {
        return userService.userInfo(uid);
    }


    @GetMapping("/preCode")
    @SystemLog(businessName = "前置验证码")
    public Result preCode() {
        return Result.success(userService.preCode());
    }

    /**
     * @param email String
     * @param type  boolean
     * @return {@link Result}
     */
    @GetMapping("/email")
    @SystemLog(businessName = "验证码")
    public Result email(@RequestParam
                        @Valid
                        @NotNull(message = "邮箱不能为空")
                        @Length(min = 6, max = 30, message = "邮箱长度必须在6到30之间")
                        @Email(message = "参数必须为邮箱")
                        String email,
                        @RequestParam
                        @Valid
                        @Length(min = 4, max = 10, message = "验证码长度必须在4到6之间")
                        @NotBlank(message = "前置验证码不为空")
                        String code,
                        @RequestParam
                        @Valid
                        @NotBlank(message = "前置验证码标识不能为空")
                        String uuid,
                        @RequestParam
                        @NotNull(message = "验证码类型不能为空")
                        int type) {
        userService.sendCode(email, code, uuid, type);
        return Result.success();
    }

    @PutMapping("/password")
    @SystemLog(businessName = "用户修改密码")
    public Result password(@RequestBody PasswordUpdateVo passwordUpdateVo) {
        return userService.passwordUpdate(passwordUpdateVo);
    }

    @PutMapping(value = "/email")
    @SystemLog(businessName = "用户修改邮箱")
    public Result email(@RequestBody EmailUpdateVo emailUpdateVo) {
        return userService.emailUpdate(emailUpdateVo);
    }
}