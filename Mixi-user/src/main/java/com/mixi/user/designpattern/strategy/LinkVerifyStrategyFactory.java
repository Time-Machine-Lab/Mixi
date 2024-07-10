package com.mixi.user.designpattern.strategy;

import com.google.common.collect.Maps;
import com.mixi.user.constants.enums.LinkVerifyStrategyEnum;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

/**
 * @NAME: LinkVerifyStrategyFactory
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/9
 */
public class LinkVerifyStrategyFactory {
    private static Map<String,LinkVerifyStrategy> map = Maps.newHashMap();
    public static LinkVerifyStrategy getInvokeStrategy(String type){
        return map.get(LinkVerifyStrategyEnum.getSecondValue(type));
    }
    public static void register(String name,LinkVerifyStrategy linkVerifyStrategy){
        if (Strings.isBlank(name) || null == linkVerifyStrategy){
            return;
        }
        map.put(name,linkVerifyStrategy);
    }
}