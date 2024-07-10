package com.mixi.server.netty.protocol;

import java.io.Serializable;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/10 21:53
 */
public class ChatroomMsg implements Serializable {

    private static final long serialVersionUID = 7718754664815537037L;

    private Long roomId;
    private Long fromUid;


}
