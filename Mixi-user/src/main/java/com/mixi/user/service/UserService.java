package com.mixi.user.service;

import com.mixi.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mixi.user.domain.vo.UserLoginVo;

/**
* @author yuech
* @description 针对表【mixi_user】的数据库操作Service
* @createDate 2024-06-25 16:18:03
*/
public interface UserService extends IService<User> {
    Object linkLogin(UserLoginVo userLoginVo);

    Object linkVerify(String email,String uid);
}
