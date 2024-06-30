package com.mixi.user.designpattern.chain.ext;


import com.mixi.user.designpattern.chain.ApproveChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @NAME: LastStepApproveChain
 * @USER: yuech
 * @Description:走到这一步代表所有的验证都已经通过
 * @DATE: 2023/12/25
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class LastStepApproveChain extends ApproveChain {
    @Override
    public boolean approve() {
        return true;
    }
}