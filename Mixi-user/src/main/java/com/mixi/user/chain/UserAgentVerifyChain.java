package com.mixi.user.chain;

import com.mixi.common.exception.ServeException;
import com.mixi.user.utils.AgentUtil;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import java.util.List;

import static com.mixi.user.constants.ChainConstant.USER_AGENT_VERIFY;
import static com.mixi.user.constants.ServeCodeConstant.USER_AGENT_ERROR;


@Chain(USER_AGENT_VERIFY)
public class UserAgentVerifyChain  extends AbstractFilterChain<AgentUtil.UserAgentInfo> {
    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(AgentUtil.UserAgentInfo userAgent) {
        if (AgentUtil.isBlank(userAgent)) {
            throw ServeException.of(USER_AGENT_ERROR);
        }
        return true;
    }
}
