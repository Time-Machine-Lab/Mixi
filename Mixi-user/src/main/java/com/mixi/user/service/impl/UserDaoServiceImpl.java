package com.mixi.user.service.impl;

import com.mixi.common.exception.ServeException;
import com.mixi.user.bean.entity.User;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.service.UserDaoService;
import io.github.service.AssistantServiceImpl;
import org.springframework.stereotype.Service;

import static com.mixi.user.constants.ServeCodeConstant.REGISTER_ERROR;

@Service
public class UserDaoServiceImpl extends AssistantServiceImpl<UserMapper, User> implements UserDaoService {

    @Override
    public boolean emailExist(String email) {
        return query().eq("email", email).count() >= 1;
    }

    @Override
    public void register(User user) {
        try {
            if (!save(user)) {
                throw ServeException.of(REGISTER_ERROR,"用户已经存在");
            }
        }catch (Exception e){
            throw ServeException.of(REGISTER_ERROR,"新建用户失败");
        }
    }

    @Override
    public User getUserByFinger(String finger) {
        return query().eq("finger", finger).one();
    }
}
