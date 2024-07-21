package com.mixi.server.service.Impl;

import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.RoomChannelManager;
import com.mixi.server.pojo.DTO.RemoveChannelReqDTO;
import com.mixi.server.service.ApiService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/14 15:17
 */
@Service
public class IApiServiceImpl implements ApiService {


    @Override
    public void removeChannelById(RemoveChannelReqDTO reqDTO) {
        MixiNettyChannel nettyChannel = MixiNettyChannel.getChannelById(reqDTO.getUid());
        RoomChannelManager.removeChannel(reqDTO.getRoomId(),nettyChannel,reqDTO.isOwner());
    }
}
