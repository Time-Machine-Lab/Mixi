package com.mixi.user.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AgentUtil {

    /**
     * 获取用户Agent信息
     * @param userAgentStr
     * @return
     */
    public static UserAgentInfo getUserAgent(String userAgentStr) {
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        String browser = userAgent.getBrowser().toString();
        String os = userAgent.getOs().toString();
        return new UserAgentInfo(browser, os);
    }

    public static boolean isBlank(UserAgentInfo userAgentInfo){
        return StringUtils.isBlank(userAgentInfo.getBrowser()) ||
        StringUtils.isBlank(userAgentInfo.getOs());
    }
    @Data
    @AllArgsConstructor
    public static class UserAgentInfo{
        private String browser;

        private String os;
    }
}
