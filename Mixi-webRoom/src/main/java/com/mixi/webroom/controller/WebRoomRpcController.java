package com.mixi.webroom.controller;

import com.mixi.webroom.domain.dto.CallBackDTO;
import com.mixi.webroom.service.WebRoomRpcService;
import com.mixi.webroom.service.WebRoomService;
import io.github.common.web.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@RestController
@RequestMapping("/webRoom")
public class WebRoomRpcController {
    @Resource
    private WebRoomRpcService webRoomRpcService;

    @PostMapping("/callBack")
    public Result<?> joinRoomCallBack(@RequestBody
                                      @Valid
                                      CallBackDTO callBackDTO) {
        return webRoomRpcService.callBack(callBackDTO);
    }
}