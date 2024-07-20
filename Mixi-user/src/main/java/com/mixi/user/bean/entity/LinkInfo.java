package com.mixi.user.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkInfo {

    private String os;

    private String browser;

    private String email;

    private String ltId;
}
