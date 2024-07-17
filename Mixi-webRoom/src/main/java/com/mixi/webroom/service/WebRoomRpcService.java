package com.mixi.webroom.service;

import io.github.common.web.Result;
import org.springframework.stereotype.Component;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@Component
public interface WebRoomRpcService {
    Result<?> createRoomCallBack(String uid, String roomId);
}
