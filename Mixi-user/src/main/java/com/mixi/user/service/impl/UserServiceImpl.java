package com.mixi.user.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mixi.user.domain.entity.User;
import com.mixi.user.domain.vo.UserLoginVo;
import com.mixi.user.service.UserService;
import com.mixi.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
* @author yuech
* @description 针对表【mixi_user】的数据库操作Service实现
* @createDate 2024-06-25 16:18:03
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Override
    public Object linkLogin(UserLoginVo userLoginVo) {

        String link = "https://example.com/verify?" + "&email=" + DigestUtil.md5Hex(userLoginVo.getEmail());
        // 设置链接的有效期为5分钟
        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofMinutes(5)); // 过期时间为当前时间加上5分钟
        String expirationStr = expiration.toString();
        // 拼接包含过期时间的链接
        String linkWithExpiration = link + "&expiration=" + DigestUtil.md5Hex(expirationStr);
//        发送链接
        sendEmail(userLoginVo.getEmail(),link);
        return null;
    }

    private void sendEmail(String email, String link) {
    }
}




