package com.mixi.webroom.common.rpc;

import io.github.common.web.Result;
import org.springframework.stereotype.Component;

/**
 * @Date 2024/7/2
 * @Author xiaochun
 */
//音视频
@Component
public class VideoService {
    public Result createRoom(){
        return Result.success();
    }
}
