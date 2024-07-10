package com.mixi.user.designpattern.chain.ext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;

/**
 * @NAME: CodeCheckApproveChain
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/4
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
@Slf4j
public class CodeCheckApproveChain  extends ApproveChain {

    //todo 补充验证码校验方式
    @Override
    public boolean approve() {
        if (Objects.equals(getCode(),getParams())){
            return getNextChain().approve();
        }
        log.debug("验证码错误");
        throw new RuntimeException(COMMON_ERROR);
    }
    public static String getCode(){
        return "000000";
    }

}