package com.mixi.user.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.user.bean.dto.LoginDTO;
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

    @ApiAuth(value= AuthType.NOT)
    @GetMapping("/code/pic")
    public Result getPicCode(){
        return userService.getPicCode();
    }

    @ApiAuth(value= AuthType.NOT)
    @PostMapping("/linkLogin")
    public Result linkLogin(
            @RequestHeader(value="User-Agent") String userAgent,
            @RequestBody  @Valid LoginDTO loginDTO){
        return userService.linkLogin(loginDTO, userAgent);
    }

    @ApiAuth(value= AuthType.NOT)
    @GetMapping("/linkVerify")
    public Result linkLoginVerify(
            @RequestHeader(value="User-Agent") String userAgent,
            @RequestParam("linkToken") String linkToken ){
        return userService.linkVerify(linkToken,userAgent);
    }

    @ApiAuth(value= AuthType.NEED)
    @GetMapping("/getUserInfo")
    public Result linkLoginVerify(
            @RequestParam(value="uid",required = false) String uid){
        return userService.getUserInfo(uid);
    }
}
