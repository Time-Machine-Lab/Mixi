package com.mixi.common.component.transfer;

import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.UserThread;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 用户信息拦截器
 * @author suifeng
 * 日期: 2024/7/5
 */
@RequiredArgsConstructor
@SuppressWarnings("all")
public class UserInfoInterceptor implements HandlerInterceptor {

    private final UserInfoTransferHandler userInfoTransferHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从请求中提取用户信息
        TokenUserInfo tokenUserInfo = userInfoTransferHandler.extractUserInfo(request);

        // 如果提取到了用户信息，就加入用户线程中
        if (tokenUserInfo != null) {
            UserThread.setUser(tokenUserInfo);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束之后移除当前用户
        UserThread.removeUser();
    }
}