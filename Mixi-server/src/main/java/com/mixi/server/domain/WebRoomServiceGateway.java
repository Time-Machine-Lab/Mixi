package com.mixi.server.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/22 18:18
 */
@Component
@FeignClient(name = "Mixi-webRoom")
@RequestMapping("/webRoom")
public interface WebRoomServiceGateway {

    @PostMapping("/callback")
    void notifyRemote();

}
