package com.mixi.user.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAgentInfo{
    private String browser;

    private String os;
}
