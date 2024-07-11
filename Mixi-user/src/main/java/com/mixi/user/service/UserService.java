package com.mixi.user.service;

import com.mixi.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mixi.user.domain.vo.InfoVo;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.domain.vo.UserRegisterVo;
import io.github.common.web.Result;

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

    Result login(UserLoginVo userLoginVo);

    Result userInfo();
}
