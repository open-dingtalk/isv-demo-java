package com.dingtalk.isv.access.common.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * http相关方法调用
 * Created by lifeng.zlf on 2016/3/20.
 */
public class HttpUtils {
    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip)) {
            return ip;
        }
        if (request.getHeader("X-Forwarded-For") != null) {
            for (String singleIP : request.getHeader("X-Forwarded-For").split(",")) {
                if (singleIP != null && !singleIP.equals("unknown")) {
                    return singleIP.trim();
                }
            }
        }
        return request.getRemoteAddr();
    }
}
