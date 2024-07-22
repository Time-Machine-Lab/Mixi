package com.mixi.user.service;


import com.mixi.common.utils.R;
import com.mixi.user.bean.dto.LoginDTO;
import io.github.common.web.Result;

public interface UserService{

    Result<?> getPicCode();
    Result<?> linkLogin(LoginDTO loginDTO,String userAgent);
    Result<?> linkVerify(String linkToken, String userAgent, String fingerprint);

    Result<?> getUserInfo(String userId);

    R<String> generateVisitorUser(String fingerprint);
}
