package com.mixi.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class EncYMLTest {

    @Test
    public void getPass() {
        System.out.println(1);
    }
}