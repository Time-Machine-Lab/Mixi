package com.mixi.common.factory;

import com.mixi.common.pojo.Ticket;
import lombok.Getter;
import com.alibaba.fastjson.JSONObject;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class TicketFactory {
    private static final Logger log = LoggerFactory.getLogger(TicketFactory.class);
    @Resource
    StringEncryptor encryptor;

    public String createTicket(Ticket ticket) {
        return encryptor.encrypt(JSONObject.toJSONString(ticket)).replaceAll("\\+","%2B");
    }

    public Ticket decryptTicket(String ticket) {
        log.info("ticket:{}", ticket);
        return JSONObject.parseObject(encryptor.decrypt(ticket.replaceAll("%2B", "+")), Ticket.class);
    }
}
