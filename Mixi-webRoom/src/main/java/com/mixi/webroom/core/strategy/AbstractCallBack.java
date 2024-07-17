package com.mixi.webroom.core.strategy;

import com.mixi.webroom.domain.dto.CallBackDTO;

/**
 * @Author：XiaoChun
 * @Date：2024/7/17 下午4:25
 */
public abstract class AbstractCallBack {

    public Boolean call(String roomId, String uid, Boolean type){
        return type ? successCallBack(roomId, uid) : failCallBack(roomId, uid);
    }

    public Boolean call(CallBackDTO callBackDTO){
        return call(callBackDTO.getRoomId(), callBackDTO.getUid(), callBackDTO.getType());
    }

    public abstract Boolean successCallBack(String roomId, String uid);

    public abstract Boolean failCallBack(String roomId, String uid);
}