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
    String[] params;

    ApproveChain nextChain;

    public static ThreadLocal<Map> res = new ThreadLocal<>();

    /**
     * 后续扩展使用策略模式
     */
    public abstract boolean approve();

    public Map checkRes(){
        Map map = res.get();
        if (Objects.isNull(map)){
            map = new HashMap();
            res.set(map);
        }
        return map;
    }

    public void setNext(ApproveChain nextChain){
        this.nextChain = nextChain;
    }






}