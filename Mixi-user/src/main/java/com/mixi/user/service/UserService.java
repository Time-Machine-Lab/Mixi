package com.mixi.user.service;

import com.mixi.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mixi.user.domain.vo.InfoVo;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.domain.vo.UserRegisterVo;

/**
* @author yuech
* @description 针对表【mixi_user】的数据库操作Service
* @createDate 2024-06-25 16:18:03
*/
public interface UserService extends IService<User> {
    Object linkLogin(String email, String type);

    Object linkVerify(String email,String uid,String type);

    Object updateInfo(String uid, InfoVo infoVo);

//    Object linkRegister(UserLoginVo userLoginVo);

    Object commonRegister(UserRegisterVo userRegisterVo);
}
