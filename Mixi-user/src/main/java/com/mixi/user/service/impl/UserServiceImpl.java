package com.mixi.user.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.mixi.common.exception.ServeException;
import com.mixi.common.utils.ThreadContext;
import com.mixi.user.bean.dto.LoginDTO;
import com.mixi.user.bean.entity.LinkInfo;
import com.mixi.user.bean.entity.User;
import com.mixi.user.domain.RedisGateway;
import com.mixi.user.service.UserService;
import com.mixi.user.utils.AgentUtil;
import com.mixi.user.utils.UserUtil;
import io.github.common.SafeBag;
import io.github.common.web.Result;
import io.github.servicechain.ServiceChainFactory;
import io.github.servicechain.bootstrap.ReturnType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Map;
import java.util.UUID;

import static com.mixi.user.constants.RedisKeyConstant.EMAIL_LINK_TOKEN_KEY;
import static com.mixi.user.constants.ServeCodeConstant.REGISTER_ERROR;
import static com.mixi.user.constants.ServeCodeConstant.REPEAT_OPERATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDaoServiceImpl userDaoService;

    @Resource
    private RedisGateway redisGateway;

    @Resource
    private ServiceChainFactory chainFactory;

    @Resource
    private UserUtil userUtil;

    @Resource
    StringEncryptor encryptor;

    @Override
    public Result<?> linkLogin(LoginDTO loginDTO,String userAgent) {

        AgentUtil.UserAgentInfo agentInfo = AgentUtil.getUserAgent(userAgent);

        chainFactory.get("linkLogin")
                .<LoginDTO>supplierMap(Map.of(
                        1,(loginDto)-> new String[]{loginDto.getPicId(),loginDto.getPicCode()},
                        2, LoginDTO::getEmail,
                        3,(loginDto)-> agentInfo
                ))
                .returnType(ReturnType.THROW)
                .execute(loginDTO);

        String email = loginDTO.getEmail();

        //生成短链
        String shortLink = generateShortLink(email, agentInfo);

        if (!redisGateway.setIfAbsent(EMAIL_LINK_TOKEN_KEY,shortLink,email)) {
            throw ServeException.of(REPEAT_OPERATION);
        }

        //发送至邮箱
        return Result.success();
    }

    private String generateShortLink(String email,AgentUtil.UserAgentInfo agentInfo){
        LinkInfo linkInfo = LinkInfo.builder()
                .browser(agentInfo.getBrowser())
                .os(agentInfo.getOs())
                .ltId(UUID.randomUUID().toString())
                .email(email).build();
        return encryptor.encrypt(JSONObject.toJSONString(linkInfo));
    }

    @Override
    public Result<?> linkVerify(String linkToken,String userAgent) {
        // 解析 linkToken

        AgentUtil.UserAgentInfo agentInfo = AgentUtil.getUserAgent(userAgent);
        ThreadContext.setData("agentInfo",agentInfo);
        SafeBag<String> token = new SafeBag<>();
        SafeBag<LinkInfo> linkInfo = new SafeBag<>();
        chainFactory.get("linkVerify")
                .<LinkInfo>supplierMap(Map.of(
                        1, (obj)-> agentInfo,
                        3, (obj)->{
                            linkInfo.setData(ThreadContext.context().getData("LinkInfo",LinkInfo.class));
                            return linkInfo.getData().getEmail();
                        }
                ))
                // 邮箱不存在则直接进行用户注册
                .failCallbackMap(Map.of(
                        3,()->{
                            User user = userUtil.newJoinUser(linkInfo.getData().getEmail(), null);
                            register(user);
                            //生成token
                        }
                ))
                // 邮箱存在则进行用户登录
                .successCallbackMap(Map.of(
                        3,()->{
                           //生成token
                        }
                ))
                .execute(linkToken);


        // 生成token
        return Result.success(Map.of("token",token.getData()));
    }

    private void register(User user){
        try {
            if (!userDaoService.save(user)) {
                throw ServeException.of(REGISTER_ERROR,"用户已经存在");
            }
        }catch (Exception e){
            throw ServeException.of(REGISTER_ERROR,"用户已经存在");
        }
    }
}




