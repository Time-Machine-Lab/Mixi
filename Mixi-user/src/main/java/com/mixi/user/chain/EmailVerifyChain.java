package com.mixi.user.chain;

import com.mixi.common.exception.ServeException;
import com.mixi.user.service.impl.UserDaoServiceImpl;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import javax.annotation.Resource;
import java.util.List;

import static com.mixi.user.constants.ChainConstant.EMAIL_EXIST_VERIFY;
import static com.mixi.user.constants.ServeCodeConstant.EMAIL_NOT_EXIST;

@Chain(EMAIL_EXIST_VERIFY)
public class EmailVerifyChain extends AbstractFilterChain<String> {

    @Resource
    private UserDaoServiceImpl userDaoService;

    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(String email) {
        if (!userDaoService.emailExist(email)) {
            throw ServeException.of(EMAIL_NOT_EXIST);
        }
        return true;
    }
}
