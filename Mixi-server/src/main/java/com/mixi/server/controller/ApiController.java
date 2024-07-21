package com.mixi.server.controller;

import com.mixi.server.pojo.DTO.RemoveChannelReqDTO;
import com.mixi.server.service.ApiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/14 15:13
 */
@RestController
@RequestMapping("/room/api")
public class ApiController {

    @Resource
    ApiService service;
    @DeleteMapping("/exit/room")
    public void removeChannelById(RemoveChannelReqDTO reqDTO){
        service.removeChannelById(reqDTO);
    }
}
