package com.mixi.user.designpattern.strategy;

public interface LinkVerifyStrategy {
    String process(String email,String uid);
}
