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

    private final String NAME = "CodeCheckApproveChain";

    //todo 补充验证码校验方式
    @Override
    public boolean approve() {
        if (getCode().equals(getParams()[0])){
            Map map = checkRes();
            map.put(NAME,getCode());
            if (Objects.isNull(getNextChain())){
                return true;
            }
            return getNextChain().approve();
        }
        log.info("验证码错误");
        throw new RuntimeException(COMMON_ERROR);
    }
    public static String getCode(){
        return "000000";
    }

}