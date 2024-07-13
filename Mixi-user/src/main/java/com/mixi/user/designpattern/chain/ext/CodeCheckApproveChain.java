package com.mixi.user.designpattern.chain.ext;

import com.mixi.user.designpattern.chain.ApproveChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;

/**
 * @NAME: CodeCheckApproveChain
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/4
 */
@Scope("prototype")
@Component("CodeCheckApproveChain")
@RequiredArgsConstructor
@Slf4j
public class CodeCheckApproveChain  extends ApproveChain {
    //todo 补充验证码校验方式
    @Override
    public Object process() {
        if (!getCode().equals(getParams()[0])){
            log.info("验证码错误");
            throw new RuntimeException(COMMON_ERROR);
        }
        return null;
    }

    @Override
    public void setNAME() {
        setNAME("CodeCheckApproveChain");
    }

    public static String getCode(){
        return "000000";
    }

}