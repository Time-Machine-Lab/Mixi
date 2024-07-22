package com.mixi.webroom.service;

import com.mixi.webroom.pojo.dto.CallBackDTO;
import io.github.common.web.Result;
import org.springframework.stereotype.Component;

/**
 * @author XiaoChun
 * @date 2024/7/16
 */
@Component
public interface WebRoomRpcService {
    Result<?> callBack(CallBackDTO callBackDTO);
}
