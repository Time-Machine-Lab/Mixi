package com.mixi.user.chain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.mixi.common.exception.ServeException;
import com.mixi.common.utils.ThreadContext;
import com.mixi.user.bean.LinkInfo;
import com.mixi.user.bean.UserAgentInfo;
import com.mixi.user.domain.RedisGateway;

import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;
import org.jasypt.encryption.StringEncryptor;


import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.mixi.user.constants.ChainConstant.EMAIL_LINK_VERIFY;
import static com.mixi.user.constants.MixiUserConstant.NIL;
import static com.mixi.user.constants.RedisKeyConstant.EMAIL_LINK_TOKEN_KEY;
import static com.mixi.user.constants.ServeCodeConstant.*;


@Chain(EMAIL_LINK_VERIFY)
public class EmailLinkVerifyChain extends AbstractFilterChain<String> {

    @Resource
    private RedisGateway redisGateway;

    @Resource
    StringEncryptor encryptor;

    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(String linkToken) {
        LinkInfo linkInfo;
        // 解密校验短链信息
        try {
            linkInfo = JSONObject.parseObject(encryptor.decrypt(linkToken), LinkInfo.class);
        }catch (Exception e){
            throw ServeException.of(INVALID_LINK_TOKEN);
        }
        if(Objects.isNull(linkInfo)){
            throw ServeException.of(INVALID_LINK_TOKEN);
        }

        // 校验短链信息是否存在
        String key = EMAIL_LINK_TOKEN_KEY.getKey(linkInfo.getEmail());
        String secrets = redisGateway.get(EMAIL_LINK_TOKEN_KEY,linkInfo.getEmail());
        // 短链信息不为空，短链信息不为NIL（保证幂等性），校验短链信息和生成短链信息是否一致
        if(StringUtils.isBlank(secrets)||!secrets.equals(linkToken)){
            throw ServeException.of(INVALID_LINK_TOKEN);
        }

        // 校验设备信息是否正确
        UserAgentInfo agentInfo = ThreadContext.context().getData("agentInfo", UserAgentInfo.class);

        if (!agentInfo.getBrowser().equals(linkInfo.getBrowser())||
                !agentInfo.getOs().equals(linkInfo.getOs())) {
            throw ServeException.of(USER_AGENT_ERROR);
        }
        // 删除redis短链数据
        if (Boolean.FALSE.equals(redisGateway.template().delete(key))) {
            throw ServeException.of(REPEAT_OPERATION,"该链接已校验");
        }
        // 将解析后的短链信息存入ThreadContext
        ThreadContext.setData("LinkInfo",linkInfo);
        return true;
    }
}
