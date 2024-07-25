package com.mixi.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XiaoChun
 * @date 2024/7/23
 */
@Data
@Builder
@AllArgsConstructor
public class Ticket {
    private String roomId;

    private String uId;
}
