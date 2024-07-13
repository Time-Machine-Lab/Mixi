package com.mixi.user.designpattern.chain;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mixi.user.utils.MapUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    public Map checkRes(){
        Map map = res.get();
        if (Objects.isNull(map)){
            map = new HashMap();
            res.set(map);
        }
        return map;
    }

    public boolean approve(){
        Object data = process();
        if (Objects.isNull(getNextChain())){
            return true;
        }
        checkRes().put(NAME,data);
        ApproveChain nextChain = getNextChain();
        return nextChain.approve();
    }

    public void setNext(ApproveChain nextChain){
        this.nextChain = nextChain;
    }
}