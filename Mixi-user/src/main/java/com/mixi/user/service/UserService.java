package com.mixi.user.service;

import com.mixi.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mixi.user.domain.vo.*;
import io.github.common.web.Result;

import java.util.Map;

/**
* @author yuech
* @description 针对表【mixi_user】的数据库操作Service
* @createDate 2024-06-25 16:18:03
*/
public interface UserService extends IService<User> {
    Result link(String email, String type);

    Result linkVerify(String email, String uid, String type);

    Result updateInfo(String uid, InfoVo infoVo);

    Result commonRegister(UserRegisterVo userRegisterVo);

    Result login(UsernameLoginVo userLoginVo);

    Result userInfo(String uid);

    Map<String, String> preCode();

    void sendCode(String email, String code, String uuid, int type);

    Result passwordUpdate(PasswordUpdateVo passwordUpdateVo);

    Result emailUpdate(EmailUpdateVo emailUpdateVo);

}
