package com.mixi.user.designpattern.factory;

import com.mixi.user.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

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
    public String getLink(String email, String uuid, String type) {

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(ip)
                .port(port)
                .path("/api/user/verify");

        builder.queryParam("email", email)
                .queryParam("uid", uuid)
                .queryParam("type", type);

        return builder.toUriString();
    }
}