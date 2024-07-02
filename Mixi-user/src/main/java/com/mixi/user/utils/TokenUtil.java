package com.mixi.user.utils;

import cn.dev33.satoken.stp.StpUtil;

public class TokenUtil {
    public static String getToken(String id){
        String loginId = id ;
        StpUtil.login(loginId);
        return StpUtil.getTokenValueByLoginId(loginId);
    }

    public static void logout(String id){
        StpUtil.logout(id);
    }
}
