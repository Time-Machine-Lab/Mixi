package com.mixi.user;

import com.mixi.user.utils.logUtils;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MixiUserApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {
        encrypt("123");

    }

    public void encrypt(String data){
        //加密
        String encrypt = stringEncryptor.encrypt(data);
        System.out.println(data + " : " + encrypt);
//        System.out.println(stringEncryptor.decrypt(encrypt));
    }
}
