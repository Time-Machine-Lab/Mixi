package com.mixi.user.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.user.aspect.annotation.SystemLog;
import com.mixi.user.domain.vo.UserLoginVo;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 描述: 云接口测试类(不用管)
 * @author suifeng
 * 日期: 2024/7/6
 */
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@ApiAuth
public class TestController {

    @PostMapping(value = "/test1")
    public Result test1(){
        return Result.success("test1");
    }

    @ApiAuth(AuthType.INNER)
    @PostMapping(value = "/test2")
    public Result test2(){
        return Result.success("test2");
    }

    @PutMapping(value = "/test3")
    public Result test3(){
        return Result.success("test3");
    }

    @DeleteMapping(value = "/test4")
    public Result test4(){
        return Result.success("test4");
    }

    @ApiAuth(AuthType.INNER)
    @GetMapping(value = "/test5")
    public Result test5(){
        return Result.success("test5");
    }

    @GetMapping(value = "/test6")
    public Result test6(){
        return Result.success("test6");
    }

    @ApiAuth(AuthType.NOT)
    @PutMapping(value = "/test7")
    public Result test7(){
        return Result.success("test7");
    }
}
