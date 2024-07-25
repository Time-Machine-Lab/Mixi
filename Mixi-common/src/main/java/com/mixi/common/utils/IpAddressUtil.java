package com.mixi.common.utils;

import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 描述: ip地址工具类
 * @author suifeng
 * 日期: 2024/7/22
 */
@Service
public class IpAddressUtil {

    public static final String X_FORWARDED_FOR = "x-forwarded-for";

    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    public static final String X_REAL_IP = "X-Real-IP";

    public static final String LOCAL_IP_V4 = "127.0.0.1";

    public static final String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";

    public static final String CDN_SRC_IP = "cdn-src-ip";

    /**
     * 获取请求IP.
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        Enumeration<?> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            if (CDN_SRC_IP.equalsIgnoreCase(name)
                    || X_FORWARDED_FOR.equalsIgnoreCase(name)
                    || PROXY_CLIENT_IP.equalsIgnoreCase(name)
                    || WL_PROXY_CLIENT_IP.equalsIgnoreCase(name)
                    || X_REAL_IP.equalsIgnoreCase(name)) {
                ip = request.getHeader(name);
            }
            if (StringUtils.isNotBlank(ip)) {
                break;
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.equals(LOCAL_IP_V4) || ip.equals(LOCAL_IP_V6)) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }
}