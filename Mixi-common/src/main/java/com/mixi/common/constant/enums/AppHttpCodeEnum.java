package com.mixi.common.constant.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS("200","操作成功"),
    // 登录
    NEED_LOGIN("401","需要登录后操作"),
    PERMISSIONS_ERROR("402","需要登录后操作"),



    QUERY_ERROR("303","参数校验出错"),


    SERVICE_ERROR("309","调用服务出现错误"),

    SYSTEM_ERROR("500","出现错误"),
    INTERFACE_NOT_DEVELOPED("501","接口暂未开发"),
    ;

    /**
     * 1xx：消息	代表请求已被接受，需要继续处理。
     * 2xx：成功	代表请求已成功被服务器接收、理解、并接受。
     * 3xx：重定向	代表需要客户端采取进一步的操作才能完成请求。
     * 4xx：客户端错误	代表客户端看起来可能发生了错误，妨碍了服务器的处理。
     * 5xx：服务器错误	代表服务器错误，服务器在处理请求的过程中发生了错误。
     */

    private String code;
    private String msg;

    AppHttpCodeEnum(String code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
