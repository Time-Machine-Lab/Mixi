package com.mixi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mixi.user.domain.entity.User;
import com.mixi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;

/**
 * @NAME: UserDaoService
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/10
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserDaoService {

    private final UserMapper userMapper;

    public int insert(User user){
        try {
            return userMapper.insert(user);
        }catch (Exception e){
            log.debug("该邮箱已经被注册！");
            throw new RuntimeException(COMMON_ERROR);
        }
    }

}