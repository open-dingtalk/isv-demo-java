package com.dingtalk.isv.access.common.log.http;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpRequestInteceptor extends HandlerInterceptorAdapter {

    private final static Logger mainLogger     = Logger.getLogger(HttpRequestInteceptor.class);
    private final static Logger accessLogger   = Logger.getLogger("CONTROLLER_PROFILE_LOG");

    @Autowired
    private HttpRequestContextHolder httpRequestContextHolder;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HttpRequestContext controllerContext = new HttpRequestContext();
            controllerContext.setStartTime(System.currentTimeMillis());
            controllerContext.setClientIp(getRemortIP(request));
            controllerContext.setRequestMethod(request.getMethod());
            controllerContext.setRequestURI(request.getRequestURI());
            controllerContext.setRequestURL(request.getRequestURL().toString());
            controllerContext.setQuery(request.getQueryString());
            httpRequestContextHolder.setHttpRequestContext(controllerContext);
        }
        return super.preHandle(request, response, handler);
    }


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
