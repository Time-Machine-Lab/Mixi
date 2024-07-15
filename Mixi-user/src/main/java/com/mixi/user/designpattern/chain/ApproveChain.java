package com.mixi.user.designpattern.chain;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

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

    String NAME;
    String[] params;
    ApproveChain nextChain;

    public static ThreadLocal<Map> res = new ThreadLocal<>();
    public abstract Object process();
    public abstract void setNAME();

    public void checkRes(String NAME, Object data){
        Map map = res.get();
        if (Objects.isNull(map)){
            map = new HashMap();
            res.set(map);
        }
        map.put(NAME,data);
    }

    public boolean approve(){
        setNAME();
        Object data = process();
        checkRes(NAME,data);
        if (Objects.isNull(getNextChain())){
            return true;
        }
        ApproveChain nextChain = getNextChain();
        return nextChain.approve();
    }

    public void setNext(ApproveChain nextChain){
        this.nextChain = nextChain;
    }
}