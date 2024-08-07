package com.mixi.webroom.utils;

import com.mixi.common.exception.ServeException;
import com.mixi.common.factory.TicketFactory;
import com.mixi.common.pojo.Ticket;
import com.mixi.common.utils.RCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Date 2024/7/7
 * @Author xiaochun
 */
@Component
public class WebRoomUtil {
    @Resource
    private TicketFactory ticketFactory;

    @Value("${mixi.verifyLinkUrl}")
    private String verifyLinkUrl;

    @Value("${mixi.socket.ip:127.0.0.1}")
    private String socketIp;

    @Value("${mixi.video.ip:127.0.0.1}")
    private String videoIp;

    public String ticket(Ticket ticket){
        return ticketFactory.createTicket(ticket);
    }

    public String link(Ticket ticket){
        return verifyLinkUrl + ticket;
    }

    public Ticket decryptLink(String key){
        Ticket ticket;
        try {
            ticket = ticketFactory.decryptTicket(key);
        } catch (Exception e) {
            throw new ServeException(RCode.TRANSCODE_ERROR);
        }
        if(Objects.isNull(ticket)){
            throw new ServeException(RCode.TRANSCODE_ERROR);
        }
        return ticket;
    }

    public String balanceSocketIp(){
        return socketIp;
    }

    public String balanceVideoIp(){
        return videoIp;
    }
}
