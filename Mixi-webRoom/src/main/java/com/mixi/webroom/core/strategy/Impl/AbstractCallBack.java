package com.mixi.webroom.core.strategy.Impl;

import com.mixi.webroom.core.strategy.CallBack;
import com.mixi.webroom.pojo.dto.CallBackDTO;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午4:25
 */
public abstract class AbstractCallBack implements CallBack {
    public Boolean call(String roomId, String uid, Boolean type){
        return type ? successCallBack(roomId, uid) : failCallBack(roomId, uid);
    }

    public Boolean call(CallBackDTO callBackDTO){
        return call(callBackDTO.getRoomId(), callBackDTO.getUid(), callBackDTO.getType());
    }
}