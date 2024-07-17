package com.mixi.common.component.transfer;

import com.mixi.common.pojo.TokenUserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static com.mixi.common.constant.constpool.TransferConstant.USER_INFO;

@Component
public class DefaultUserInfoTransferHandler implements UserInfoTransferHandler {

    @Override
    public void passUserInfo(ServerWebExchange exchange, TokenUserInfo tokenUserInfo) {
        if (tokenUserInfo != null) {
            String userInfo = tokenUserInfo.getUserId() + " " + tokenUserInfo.getUsername() + " " + Arrays.toString(tokenUserInfo.getRoles());
            exchange.getResponse().getHeaders().add(USER_INFO, userInfo);
        }
    }

    @Override
    public TokenUserInfo extractUserInfo(HttpServletRequest request) {
        String userInfo = request.getHeader(USER_INFO);
        if (userInfo != null && !userInfo.isEmpty()) {
            return convertStringToTokenUserInfo(userInfo);
        }
        return null;
    }

    private TokenUserInfo convertStringToTokenUserInfo(String userInfo) {
        String[] split = userInfo.split(" ");
        int[] roles = Arrays.stream(split[2].substring(1, split[2].length() - 1).split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        return new TokenUserInfo(split[0], split[1], roles);
    }
}