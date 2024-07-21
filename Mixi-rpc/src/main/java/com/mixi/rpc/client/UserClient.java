package com.mixi.rpc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mixi-user-service")
@Component
public interface UserClient {

    @GetMapping("/api/user/visit/generate")
    String generateVisitorUser(@RequestParam String fingerprint);
}