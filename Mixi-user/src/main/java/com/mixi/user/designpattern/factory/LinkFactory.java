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

    private final String AGREEMENT = "http";
    private final String PATH = "/api/user/verify";

    @Value("${env.ip}")
    private String ip;

    @Value("${env.port}")
    private String port;
    public String getLink(String email, String uuid, String type) {

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(AGREEMENT)
                .host(ip)
                .port(port)
                .path(PATH);

        builder.queryParam("email", email)
                .queryParam("type", type)
                .queryParam("uid", uuid);

        return builder.toUriString();
    }
}