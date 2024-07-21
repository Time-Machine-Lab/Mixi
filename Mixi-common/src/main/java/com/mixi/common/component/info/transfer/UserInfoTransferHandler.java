package com.mixi.common.component.info.transfer;
import com.mixi.common.pojo.TokenUserInfo;

/**
 * 描述: 定义用户信息的打包和拆包逻辑接口
 * @author suifeng
 * 日期: 2024/7/17
 */
public interface UserInfoTransferHandler {

    /**
     * 用于将用户信息打包成字符串传递给下游微服务
     */
    String packageUserInfo(TokenUserInfo tokenUserInfo);

    /**
     * 从userInfo字符串中提取用户信息
     */
    TokenUserInfo extractUserInfo(String userInfo);
}