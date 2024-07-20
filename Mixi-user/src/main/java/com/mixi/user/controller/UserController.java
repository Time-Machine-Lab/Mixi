package com.mixi.user.controller;

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

    @PostMapping("/linkLogin")
    public Result linkLogin(
            @RequestHeader(value="User-Agent") String userAgent,
            @RequestBody  @Valid LoginDTO loginDTO){
        return userService.linkLogin(loginDTO, userAgent);
    }

    @GetMapping("/linkVerify")
    public Result linkLoginVerify(
            @RequestHeader(value="User-Agent") String userAgent,
            @RequestParam("linkToken") String linkToken ){
        return Result.success();
    }
}
