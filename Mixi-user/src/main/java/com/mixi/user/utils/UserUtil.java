package com.mixi.user.utils;

import com.alibaba.nacos.api.utils.StringUtils;
import com.mixi.common.exception.ServeException;
import com.mixi.user.bean.entity.User;
import io.github.id.snowflake.SnowflakeGenerator;
import io.github.id.snowflake.SnowflakeRegisterException;
import io.github.util.encryption.MD5EncryptionUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;

import static com.mixi.user.constants.MixiUserConstant.NIL;

@Component
public class UserUtil {

    @Value("${mixi.user.avatar:https://tmlgenius.github.io/assistant-docs/img/logo.svg}")
    private String avatar;

    @Value("${mixi.user.resume:No Description}")
    private String resume;

    @Value("${mixi.user.nickNamePrefix:Mixi用户}")
    private String nicknamePrefix;

    @Value("${mixi.user.nickNameLen:10}")
    private int nicknameLen;

    @Getter
    @Value("${mixi.user.userRole:1001}")
    private String userRole;

    @Value("${mixi.user.touristRole:1002}")
    private String touristRole;

    @Resource
    private SnowflakeGenerator snowflakeGenerator;

    @Resource
    MD5EncryptionUtils md5EncryptionUtils;

    public User newJoinUser(String username, String email, String password, String fingerprint) {
        try {
            return User.builder()
                    .id(String.valueOf(snowflakeGenerator.generate()))
                    .sex(NIL)
                    .username(username)
                    .email(email)
                    .password(StringUtils.isBlank(password) ? NIL : md5EncryptionUtils.Encipher(password))
                    .avatar(avatar)
                    .resume(resume)
                    .nickname(nicknamePrefix + RandomUtil.generateRandomString(nicknameLen))
                    .delFlag(Boolean.FALSE.toString())
                    .finger(StringUtils.isBlank(fingerprint) ? NIL : fingerprint)
                    .roles(StringUtils.isBlank(fingerprint) ? userRole : touristRole)
                    .build();
        } catch (SnowflakeRegisterException e) {
            throw ServeException.SystemError("id生成失败");
        }
    }

    public User newJoinUser(String email, String password) {
        return newJoinUser(email, email, password, null);
    }

    // 根据指纹生成新用户
    public User newJoinUser(String fingerprint) {
        // 用户名随机
        return newJoinUser(RandomUtil.generateRandomString(nicknameLen), fingerprint, null, fingerprint);
    }
}
