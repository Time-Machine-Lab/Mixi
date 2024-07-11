package com.mixi.user.designpattern.strategy;

import org.springframework.beans.factory.InitializingBean;

public interface LinkVerifyStrategy extends InitializingBean {
    String process(String email,String uid);
}
