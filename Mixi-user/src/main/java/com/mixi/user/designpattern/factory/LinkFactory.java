package com.mixi.user.designpattern.factory;

import com.mixi.user.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * @NAME: LinkFactory
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/1
 */
public class LinkFactory {
    @Value("env.ip")
    private String ip;

    @Value("env.port")
    private String port;

    public String  getLink(String email,String uuid){
        String link = "http://"+ ip + ":" + port + "/api/user/verify?" + "&email=" + email + "&uid=" + uuid;
        return link;
    }
}