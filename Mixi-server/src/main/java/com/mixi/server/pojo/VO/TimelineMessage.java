package com.mixi.server.pojo.VO;

import lombok.Data;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/16 19:10
 */
@Data
public class TimelineMessage {

    /*
     * 消息ID 以房间为维度代替常用的雪花Id，减小锁的细粒度，同时保证严格递增
     **/
    private Integer id;
    /*
     * 发送方uid
     **/
    private String fromId;
    private String roomId;
    private int cmd;
    private String content;
}
