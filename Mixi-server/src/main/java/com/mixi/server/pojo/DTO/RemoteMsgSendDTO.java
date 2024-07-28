package com.mixi.server.pojo.DTO;

import lombok.Data;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/22 18:30
 */
@Data
public class RemoteMsgSendDTO {

    private String uid;
    private String roomId;
    private String callback;
    private boolean type;

    public static RemoteMsgSendDTO convertMsg(String roomId,String uid,String callback,boolean type){
        RemoteMsgSendDTO sendDTO = new RemoteMsgSendDTO();
        sendDTO.setUid(uid);
        sendDTO.setRoomId(roomId);
        sendDTO.setCallback(callback);
        sendDTO.setType(type);
        return sendDTO;
    }

}
