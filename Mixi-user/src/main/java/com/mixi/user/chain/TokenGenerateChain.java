package com.mixi.user.chain;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mixi.common.component.token.service.SaTokenService;
import com.mixi.common.pojo.TokenUserInfo;
import com.mixi.common.utils.ThreadContext;
import com.mixi.user.bean.entity.User;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import javax.annotation.Resource;
import java.util.List;

import static com.mixi.common.constant.constpool.TransferConstant.FINGER_PRINT;
import static com.mixi.user.constants.ChainConstant.TOKEN_GENERATE;

@Chain(TOKEN_GENERATE)
public class TokenGenerateChain extends AbstractFilterChain<User> {

    @Resource
    private SaTokenService tokenService;


    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(User userInfo) {
        TokenUserInfo tokenUserInfo = TokenUserInfo.builder()
                .userId(userInfo.getId())
                .username(userInfo.getUsername())
                .roles(new int[] {Integer.parseInt(userInfo.getRoles())})
                .addField(FINGER_PRINT, userInfo.getFinger())
                .build();
        String token = tokenService.loginAndGenerateToken(tokenUserInfo);
        ThreadContext.setData("token",token);
        return !StringUtils.isBlank(token);
    }
}
