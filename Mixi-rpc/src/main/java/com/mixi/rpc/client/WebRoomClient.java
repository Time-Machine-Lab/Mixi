package com.mixi.rpc.client;

import com.mixi.rpc.domain.dto.CallBackDTO;
import io.github.common.web.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author XiaoChun
 * @date 2024/7/19
 */
@FeignClient(name = "webRoom-service", path = "/webRoom")
public interface WebRoomClient {
    @PostMapping("/callBack")
    Result<?> callBack(@RequestBody @Valid CallBackDTO callBackDTO);
}