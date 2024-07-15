package com.mixi.user.designpattern.chain.ext;

import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.handler.SystemException;
import com.mixi.user.service.impl.RedisDaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.CODE_ERR;
import static com.mixi.user.constants.CommonConstant.COMMON_ERROR;
import static com.mixi.user.constants.RedisConstant.REDIS_KEY_CAPTCHA;

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

    private final RedisDaoService redisDaoService;
    @Override
    public Object process() {
        String code = getCode();
        if (!code.equals(getParams()[1])){
            log.info("code err");
            throw new SystemException(CODE_ERR);
        }
        return code;
    }

    @Override
    public void setNAME() {
        setNAME("CodeCheckApproveChain");
    }

    public String getCode(){
        return (String) redisDaoService.query(REDIS_KEY_CAPTCHA + getParams()[0] + ":" + getParams()[2]);
    }

}