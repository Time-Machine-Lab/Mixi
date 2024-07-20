package com.mixi.user.service.impl;

import com.mixi.user.bean.entity.User;
import com.mixi.user.mapper.UserMapper;
import com.mixi.user.service.UserDaoService;
import io.github.service.AssistantServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserDaoServiceImpl extends AssistantServiceImpl<UserMapper, User> implements UserDaoService {

    @Override
    public boolean emailExist(String email) {
        return query().eq("email", email).count() >= 1;
    }
}
