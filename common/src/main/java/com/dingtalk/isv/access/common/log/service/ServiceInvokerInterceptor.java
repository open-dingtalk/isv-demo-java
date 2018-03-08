package com.dingtalk.isv.access.common.log.service;

import com.dingtalk.isv.access.common.log.context.RpcContext;
import com.dingtalk.isv.access.common.log.http.HttpRequestContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lifeng.zlf on 2016/4/12.
 */
public class ServiceInvokerInterceptor extends HandlerInterceptorAdapter {

    private final static Logger mainLogger     = Logger.getLogger(ServiceInvokerInterceptor.class);
    private final static Logger accessLogger   = Logger.getLogger("CONTROLLER_PROFILE_LOG");

    @Autowired
    private HttpRequestContextHolder httpRequestContextHolder;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = null==request.getAttribute("ISV-TRACEID")?null:request.getAttribute("ISV-TRACEID").toString();
        accessLogger.info("traceIdxxxxxxx"+traceId);
        accessLogger.info("getRequestURL"+request.getRequestURL());
        if(!StringUtils.isEmpty(traceId)){
            RpcContext.traceId.set(traceId);
        }else{
            request.setAttribute("ISV-TRACEID",RpcContext.traceId.get());
        }
        accessLogger.info("traceId_request:"+request.getAttribute("ISV-TRACEID"));
        return super.preHandle(request, response, handler);
    }

}