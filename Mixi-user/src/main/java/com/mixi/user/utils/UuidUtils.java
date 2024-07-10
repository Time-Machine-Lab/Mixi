package com.mixi.user.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @NAME: UuidUtils
 * @USER: yuech
 * @Description:
 * @DATE: 2023/11/29
 */
@Component
public class UuidUtils {
    private Integer workerId = 0;
    private Integer datacenterId = 1;
    private final Snowflake snowflake = new Snowflake(workerId, datacenterId);
    private static volatile UuidUtils instance = null;

    private UuidUtils() {
    };
    public static synchronized UuidUtils getInstance() {
        if (instance == null) {
            synchronized (UuidUtils.class) {
                if (instance == null) {
                    instance = new UuidUtils();
                }
            }
        }
        return instance;
    }


    @PostConstruct  //构造后开始执行，加载初始化工作
    public void init() {
        try {
            //获取本机的ip地址编码
            workerId = Math.toIntExact(NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()));
//            log.info("当前机器的workerId: " + workerId);
        } catch (Exception e) {
//            log.warn("当前机器的workerId获取失败 ----> " + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId) {
        Snowflake snowflake = new Snowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    //测试
    public static String creatUuid() {
        return new String(String.valueOf((getInstance().snowflakeId())));
        //1277896081711169536
    }
}