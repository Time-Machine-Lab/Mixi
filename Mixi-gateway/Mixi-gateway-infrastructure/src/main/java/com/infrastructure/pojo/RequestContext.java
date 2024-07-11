package com.infrastructure.pojo;

import com.mixi.common.pojo.ApiInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 请求上下文对象，用于在过滤器之间传递请求信息和ApiInfo
 * @author suifeng
 * 日期: 2024/7/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestContext {

    private ApiInfo apiInfo;

    private String url;

    private String token;
}