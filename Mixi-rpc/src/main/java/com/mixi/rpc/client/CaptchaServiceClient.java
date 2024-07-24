package com.mixi.rpc.client;

import com.mixi.rpc.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * @Date 2023/12/12
 * @Author xiaochun
 */
@Component
@FeignClient(name = "tml-captcha-service", path = "/captcha")
public interface CaptchaServiceClient {
    @GetMapping("/email")
    Result<Map<String, String>> email(@RequestParam String to);

    @GetMapping("/image")
    Result<Map<String, String>> image();
}