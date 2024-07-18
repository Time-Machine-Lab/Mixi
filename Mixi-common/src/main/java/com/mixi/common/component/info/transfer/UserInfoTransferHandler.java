package com.mixi.common.component.info.transfer;

import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 定义网关传递与下游微服务接收的通用逻辑
 * @author suifeng
 * 日期: 2024/7/17
 */
public interface UserInfoTransferHandler {

    /**
     * 将用户信息传递给下游微服务
     */
    void passUserInfo(ServerWebExchange exchange, TokenUserInfo tokenUserInfo);

    /**
     * 从请求中提取用户信息
     */
    TokenUserInfo extractUserInfo(HttpServletRequest request);
}