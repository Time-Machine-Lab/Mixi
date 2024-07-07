package com.mixi.server.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 19:50
 */
@Component
public class MixiHandlerRouter {

    private static Logger log = LoggerFactory.getLogger(MixiHandlerRouter.class);

    private static Map<String,MixiHandler> mixiHandlerMap;
    private ApplicationContext applicationContext;
    @Autowired
    public MixiHandlerRouter(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    @PostConstruct
    private void init(){
        mixiHandlerMap = applicationContext.getBeansOfType(MixiHandler.class);
        log.info(mixiHandlerMap.size()+"beans of MixiHandler to be load...");
    }


}
