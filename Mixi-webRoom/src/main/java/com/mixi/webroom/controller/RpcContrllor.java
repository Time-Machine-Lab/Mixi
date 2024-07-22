package com.mixi.webroom.controller;

import com.mixi.rpc.client.CaptchaServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author：XiaoChun
 * @Date：2024/7/11 下午6:16
 */
@RestController
@RequestMapping("/sss")
public class RpcContrllor {
    @Resource
    CaptchaServiceClient captchaServiceClient;

    @GetMapping("/test")
    public Object rpcTest(){
        return captchaServiceClient.image();
    }
}
