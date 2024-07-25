package com.mixi.webroom.utils;

import com.mixi.common.factory.TicketFactory;
import com.mixi.common.pojo.Ticket;
import com.mixi.webroom.core.exception.ServerException;
import com.mixi.webroom.pojo.entity.WebRoom;
import com.mixi.webroom.pojo.enums.ResultEnums;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Date 2024/7/7
 * @Author xiaochun
 */
@Component
public class WebRoomUtil {
    @Resource
    private TicketFactory ticketFactory;

    @Value("${room.ip:127.0.0.1}")
    private String ip;

    @Value("${room.port:9020}")
    private String port;

    public String link(Ticket ticket){
        return ticketFactory.createTicket(ticket);
    }

    public Ticket decryptLink(String key){
        Ticket ticket;
        try {
            ticket = ticketFactory.decryptTicket(key);
        } catch (Exception e) {
            throw new ServerException(ResultEnums.TRANSCODE_ERROR);
        }
        if(Objects.isNull(ticket)){
            throw new ServerException(ResultEnums.TRANSCODE_ERROR);
        }
        return ticket;
    }
}
