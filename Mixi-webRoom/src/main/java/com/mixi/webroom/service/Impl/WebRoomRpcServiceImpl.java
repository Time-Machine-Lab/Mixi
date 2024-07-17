package com.mixi.webroom.service.Impl;

import com.mixi.webroom.service.WebRoomRpcService;
import io.github.common.web.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@Service
public class WebRoomRpcServiceImpl implements WebRoomRpcService {
    /*
    * 处理回调
    * */
    @Override
    public Result<?> createRoomCallBack(String uid, String roomId) {

        return null;
    }
}
