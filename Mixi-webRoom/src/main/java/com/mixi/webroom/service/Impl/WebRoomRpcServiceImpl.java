package com.mixi.webroom.service.Impl;

import com.mixi.webroom.core.enums.ResultEnums;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.core.strategy.CallBack;
import com.mixi.webroom.core.strategy.CallBackStrategy;
import com.mixi.webroom.domain.dto.CallBackDTO;
import com.mixi.webroom.service.WebRoomRpcService;
import com.mixi.webroom.utils.RedisUtil;
import io.github.common.web.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@Service
public class WebRoomRpcServiceImpl implements WebRoomRpcService {
    @Resource
    private CallBackStrategy callBackStrategy;

    /*
    * 处理回调
    * */
    @Override
    public Result<?> callBack(CallBackDTO callBackDTO) {
        Boolean b = callBackStrategy.getStrategy(callBackDTO.getCallBackName()).call(callBackDTO);
        if(!callBackStrategy.getStrategy(callBackDTO.getCallBackName()).call(callBackDTO)){
            throw new ServerException(ResultEnums.CALLBACK_EXECUTE_ERROR);
        }
        return Result.success();
    }
}