package com.plmm.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: plmmProject
 * @Description:
 * @Date: 2020/4/30 11:48
 */
public class ClientIpUtils {
    /**
     * 获取请求的ip地址信息，兼容被代理的情况
     * @param request
     * @return ip地址
     */
    public static String getClientIP(HttpServletRequest request) {
        // 通过代理判断
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && StringUtils.indexOf(ip, ',') != -1) {
            String[] ips = StringUtils.split(ip, ',');
            ip = ips[0];
            if (ip.equalsIgnoreCase("unknown") && ips.length > 1) {
                ip = ips[1];
            }
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            // WebLogic Plug-In Enabled
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        // 代理没有设置，用request的
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
