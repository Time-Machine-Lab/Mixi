package com.mixi.webroom.controller;

import com.mixi.webroom.service.WebRoomRpcService;
import com.mixi.webroom.service.WebRoomService;
import io.github.common.web.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@RestController
@RequestMapping("/webRoom")
public class WebRoomRpcController {
    @Resource
    private WebRoomRpcService webRoomRpcService;

    @PostMapping("/joinRoomCallBack")
    public Result<?> joinRoomCallBack(@RequestPart String uid,
                                        @RequestPart String roomId) {
        return webRoomRpcService.joinRoomCallBack(uid, roomId);
    }
}