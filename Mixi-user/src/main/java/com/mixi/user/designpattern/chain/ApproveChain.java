package com.mixi.user.designpattern.chain;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @NAME: Approve
 * @USER: yuech
 * @Description:
 * @DATE: 2023/12/25
 */

@Data
@Scope("prototype")
@Component
public abstract class ApproveChain{

    /**
     * 后续扩展使用策略模式
     */

    String params;

    ApproveChain nextChain;

    public void setNext(String params,ApproveChain nextChain){
        this.params = params;
        this.nextChain = nextChain;
    }

    public abstract boolean approve();
}