package com.mixi.user.designpattern.chain.ext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.domain.entity.User;
import com.mixi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;

/**
 * @NAME: EmailCheckApproveChain
 * @USER: yuech
 * @Description:看邮箱是否存在于数据库
 * @DATE: 2024/6/28
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailCheckApproveChain extends ApproveChain {

    private final UserMapper userMapper;

    @Override
    public boolean approve() {
        if (!Objects.isNull(userMapper.selectOne(new QueryWrapper<User>().eq("email",getParams())))){
            return getNextChain().approve();
        }
        log.debug("邮箱未注册");
        throw new RuntimeException(COMMON_ERROR);
    }
}