package com.mixi.webroom.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author：XiaoChun
 * @Date：2024/7/15 下午4:56
 */
@Data
@AllArgsConstructor
@Builder
public class JoinVO {
    private String videoIp;

    private String socketIp;

    private String ticket;
}
