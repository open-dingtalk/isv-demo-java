package com.dingtalk.isv.access.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.log.http.HttpRequestContext;
import com.dingtalk.isv.access.common.log.http.HttpRequestContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
public class ControllorAOP {

    private final static Logger           mainLogger       = Logger.getLogger(ControllorAOP.class);
    private final static Logger           bizLogger = Logger.getLogger("CONTROLLER_PROFILE_LOG");

    @Autowired
    private HttpRequestContextHolder httpRequestContextHolder;

    @Around("execution(* com.dingtalk.isv.access.web.controller..*.*(..))")
    public Object handler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        List<Object> parmsList = new ArrayList<Object>();
        try {
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            if (signature != null && proceedingJoinPoint.getArgs() != null) {
                Method method = signature.getMethod();
                Annotation[][] methodAnnotations = method.getParameterAnnotations();
                Object[] argArr = proceedingJoinPoint.getArgs();
                parmsList = Arrays.asList(argArr);
            }
        } catch (Exception e) {
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "parse parms error"
            ), e);
        }
        Object retVal = proceedingJoinPoint.proceed();
        try {
            HttpRequestContext httpRequestContext = httpRequestContextHolder.getHttpRequestContext();
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("clientIp", httpRequestContext.getClientIp()),
                    LogFormatter.KeyValue.getNew("requestMethod", httpRequestContext.getRequestMethod()),
                    LogFormatter.KeyValue.getNew("requestURI", httpRequestContext.getRequestURI()),
                    LogFormatter.KeyValue.getNew("requestURL", httpRequestContext.getRequestURL()),
                    LogFormatter.KeyValue.getNew("query", httpRequestContext.getQuery()),
                    LogFormatter.KeyValue.getNew("rt", System.currentTimeMillis() - httpRequestContext.getStartTime()),
                    LogFormatter.KeyValue.getNew("parms", JSON.toJSONString(parmsList)),
                    LogFormatter.KeyValue.getNew("return", JSON.toJSONString(retVal))
            ));
            if(!JSON.toJSONString(retVal).contains("\"errcode\":\"0\"")){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "controller请求失败",
                        LogFormatter.KeyValue.getNew("clientIp", httpRequestContext.getClientIp()),
                        LogFormatter.KeyValue.getNew("requestMethod", httpRequestContext.getRequestMethod()),
                        LogFormatter.KeyValue.getNew("requestURI", httpRequestContext.getRequestURI()),
                        LogFormatter.KeyValue.getNew("requestURL", httpRequestContext.getRequestURL()),
                        LogFormatter.KeyValue.getNew("query", httpRequestContext.getQuery()),
                        LogFormatter.KeyValue.getNew("rt", System.currentTimeMillis() - httpRequestContext.getStartTime()),
                        LogFormatter.KeyValue.getNew("parms", JSON.toJSONString(parmsList)),
                        LogFormatter.KeyValue.getNew("return", JSON.toJSONString(retVal))
                ));
            }
        } catch (Exception e) {
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "output log error"
            ), e);
        }
        return retVal;
    }
}
