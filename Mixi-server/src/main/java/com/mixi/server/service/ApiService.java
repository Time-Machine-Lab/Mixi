package com.mixi.server.service;

import com.mixi.server.pojo.DTO.RemoveChannelReqDTO;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/14 15:18
 */
public interface ApiService {

    void removeChannelById(RemoveChannelReqDTO reqDTO);

}
