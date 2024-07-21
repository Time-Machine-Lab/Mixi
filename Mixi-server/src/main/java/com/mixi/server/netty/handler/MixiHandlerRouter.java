package com.mixi.server.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:50
 */
@Component
public class MixiHandlerRouter {

    private static Logger log = LoggerFactory.getLogger(MixiHandlerRouter.class);

    private static final Map<Integer,MixiHandler> MIXI_HANDLER_MAP = new HashMap<>();
    private ApplicationContext applicationContext;
    @Autowired
    public MixiHandlerRouter(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    @PostConstruct
    private void init(){
        Map<String, MixiHandler> map = applicationContext.getBeansOfType(MixiHandler.class);
        map.forEach((k,v)->{
            int mark = v.getMark();
            MIXI_HANDLER_MAP.put(mark,v);
        });
        log.info(MIXI_HANDLER_MAP.size()+"beans of MixiHandler to be load...");
    }

    public static MixiHandler route(int cmd){
        return MIXI_HANDLER_MAP.get(cmd);
    }
}
