package com.mixi.user.designpattern.factory;

import com.mixi.user.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @NAME: LinkFactory
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/1
 */
@Component
public class LinkFactory {
    @Value("${env.ip}")
    private String ip;

    @Value("${env.port}")
    private String port;

    public String  getLink(String email,String uuid,String type){
        String link = "http://"+ ip + ":" + port + "/api/user/verify?" + "&email=" + email + "&uid=" + uuid + "&type=" + type;
        return link;
    }
}