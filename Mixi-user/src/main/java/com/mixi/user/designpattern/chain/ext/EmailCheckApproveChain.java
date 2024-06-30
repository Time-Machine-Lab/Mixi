package com.mixi.user.designpattern.chain.ext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.domain.entity.User;
import com.mixi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @NAME: EmailCheckApproveChain
 * @USER: yuech
 * @Description:看邮箱是否存在于数据库
 * @DATE: 2024/6/28
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class EmailCheckApproveChain extends ApproveChain {

    private final UserMapper userMapper;

    @Override
    public boolean approve() {
        if (!Objects.isNull(userMapper.selectOne(new QueryWrapper<User>().eq("email",getParams())))){
            return getNextChain().approve();
        }
        throw new RuntimeException("邮箱未注册");
    }
}