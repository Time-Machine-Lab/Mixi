package com.mixi.common.config;

import com.mixi.common.component.info.UserInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnClass(DispatcherServlet.class)
@RequiredArgsConstructor
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final UserInfoInterceptor userInfoInterceptor;

    // 注册了一个用户信息拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInfoInterceptor);
    }
}