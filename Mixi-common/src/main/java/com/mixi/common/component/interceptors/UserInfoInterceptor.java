package com.mixi.common.component.interceptors;

import cn.hutool.core.util.StrUtil;
import com.mixi.common.utils.UserThread;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mixi.common.constant.constpool.TransferConstant.USER_INFO;

/**
 * 描述: 用户信息拦截器
 * @author suifeng
 * 日期: 2024/7/5
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从请求体中拿到用户信息
        String userInfo = request.getHeader(USER_INFO);

        // 如果拿到了用户信息，就加入用户线程中
        if (StrUtil.isNotBlank(userInfo)) UserThread.setUser(userInfo);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束之后移除当前用户
        UserThread.removeUser();
    }
}
