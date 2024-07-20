package com.mixi.user.constants;

import com.mixi.common.exception.ServeCode;

public class ServeCodeConstant {
    public static final ServeCode PIC_CODE_VERIFY_ERROR = new ServeCode("10001", "图片验证码错误","user:pic:error");

    public static final ServeCode EMAIL_NOT_EXIST = new ServeCode("10002", "邮箱不存在","user:email:notExist");

    public static final ServeCode USER_AGENT_ERROR = new ServeCode("10003", "登录设备异常","user:agent:error");

    public static final ServeCode REPEAT_OPERATION = new ServeCode("10004", "重复操作:%s","user:operation:repeat");
    public static final ServeCode INVALID_LINK_TOKEN = new ServeCode("10005", "验证链接错误","user:link:verify:error");

    public static final ServeCode REGISTER_ERROR = new ServeCode("10006", "注册失败:%s","user:register:error");

}
