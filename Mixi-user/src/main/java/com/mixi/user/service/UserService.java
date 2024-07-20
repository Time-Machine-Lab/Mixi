package com.mixi.user.service;


import com.mixi.user.bean.dto.LoginDTO;
import io.github.common.web.Result;

public interface UserService{

    Result<?> getPicCode();
    Result<?> linkLogin(LoginDTO loginDTO,String userAgent);

    Result<?> linkVerify(String linkToken,String userAgent);
}
