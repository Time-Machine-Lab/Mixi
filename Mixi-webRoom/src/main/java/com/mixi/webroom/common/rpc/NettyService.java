package com.mixi.webroom.common.rpc;

import io.github.common.web.Result;
import org.springframework.stereotype.Component;

/**
 * @Date 2024/7/2
 * @Author xiaochun
 */
@Component
public class NettyService {
    public Result createRoom(){
        return Result.success();
    }
}
