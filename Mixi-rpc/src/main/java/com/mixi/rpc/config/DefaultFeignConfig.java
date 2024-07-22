package com.mixi.rpc.config;

import com.mixi.common.component.info.transfer.DefaultUserInfoTransferHandler;
import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.ThreadContext;
import com.mixi.common.utils.UserThread;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import static com.mixi.common.constant.constpool.TransferConstant.*;

/**
 * 描述: 用于远程调用时自动添加用户信息到请求头
 * @author suifeng
 * 日期: 2024/7/19
 */
@RequiredArgsConstructor
public class DefaultFeignConfig {

    private final DefaultUserInfoTransferHandler userInfoTransferHandler;

    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
        return requestTemplate -> {
            TokenUserInfo tokenUserInfo = UserThread.get();
            if (tokenUserInfo != null) {
                String userInfo = userInfoTransferHandler.packageUserInfo(tokenUserInfo);
                requestTemplate.header(USER_INFO, userInfo);
            }
            requestTemplate.header(IP_ADDRESS, ThreadContext.getData(IP_ADDRESS).toString());
            requestTemplate.header(MACHINE_CODE, ThreadContext.getData(MACHINE_CODE).toString());
        };
    }
}
