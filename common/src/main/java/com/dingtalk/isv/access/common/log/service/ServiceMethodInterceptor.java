package com.dingtalk.isv.access.common.log.service;
import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.common.log.context.RpcContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ServiceMethodInterceptor implements MethodInterceptor, InitializingBean {
    private static final Logger methodProfileLog = LoggerFactory.getLogger(ServiceMethodInterceptor.class);

    private static final String PROFILE_LOG_VERSION = "2.0";
    private static final String filterId = "methodProfile";
    private static final char SPLIT_POINT = '.';
    private static final char SEPARATE = 0x01;
    private static String IP;
    static {
        try {
            IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            methodProfileLog.error("InetAddress.getLocalHost().getHostAddress() throw UnknownHostException.", e);
        }
    }
    //用于解决嵌套profile拦截器收集日志打印,最终打成一条日志
    private static ThreadLocal<Boolean> profileStarter = new ThreadLocal<Boolean>() {
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public void afterPropertiesSet() throws Exception {

    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String className = methodInvocation.getMethod().getDeclaringClass().getName();
        String simpleName = methodInvocation.getMethod().getDeclaringClass().getSimpleName();
        String methodName = methodInvocation.getMethod().getName();
        //String filterId = LogExpressionParser.getResultFilterId(methodInvocation.getMethod().getDeclaringClass(), methodName);
        String filterId = "";
        //开始运行时间戳
        long beginTime = System.currentTimeMillis();
        //方法运行结果
        Object result = null;
        boolean isFilterResult = isFilterResult(methodInvocation.getMethod().getAnnotations());
        Object[] arguments = getFilterArgs(methodInvocation.getArguments(), methodInvocation.getMethod().getParameterAnnotations());
        if (!isCollectorStart()) {
            initCollectorStarter();
        }else{
            //如果该线程再次进来是收集的入口,判断前面的接口已经开启过收集头,那么就记录profile日志即可
            long startTime = System.currentTimeMillis();
            result = methodInvocation.proceed();
            return result;
        }
        //鹰眼ID的获取和生成
        String traceId = null;
        String appName = null;
        String appIp = null;

        long endTime = 0l;//运行结束时间戳
        Integer success = Integer.valueOf(1);//方法运行正常标识
        try {
            result = methodInvocation.proceed();
            endTime = System.currentTimeMillis();
            return result;
        } catch (Throwable e) {
            endTime = System.currentTimeMillis();
            success = Integer.valueOf(0);//方法运行异常标识
            result = e.getMessage();
            throw e;
        } finally {
            long timestamp = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder();
            sb.append(PROFILE_LOG_VERSION).append(SEPARATE)
                    .append(timestamp).append(SEPARATE)
                    .append(RpcContext.traceId.get()).append(SEPARATE)
                    .append(IP).append(SEPARATE)
                    .append(className).append(".").append(methodName).append(SEPARATE)
                    .append((endTime - beginTime)).append(SEPARATE)
                    .append(parseObject(arguments)).append(SEPARATE)
                    .append(SEPARATE)
                    .append(parseObject(result)).append(SEPARATE)
                    .append(success);
            methodProfileLog.info(sb.toString());
            profileStarter.set(Boolean.FALSE);//最后收集标识归位,该线程收集结束
        }
    }

    public static String parseObject(Object arguments) throws IOException {
        try {
            return JSON.toJSONString(arguments);
        } catch (Exception e1) {
            methodProfileLog.error("serializeObject Exception arguments:" + arguments, e1);
            try {
                return ToStringBuilder.reflectionToString(arguments);
            } catch (Exception e2) {
                methodProfileLog.error("ToStringBuilder.reflectionToString Exception : " + e2.getMessage(), e2);
                return String.valueOf(arguments);
            }
        }
    }

    public boolean isCollectorStart() {
        return profileStarter.get();
    }

    /**
     *
     */
    private void initCollectorStarter() {
        //判断该线程运行到这里该方法是否是收集的开始者
        if (!profileStarter.get()) {
            //如果是False,说明该线程是第一次进来该拦截器的入口，拦截器会认为该方法为profile日志收集的入口,然后会标识为True,说明开始收集
            profileStarter.set(Boolean.TRUE);
        }
    }



    private Object[] getFilterArgs(Object[] arguments, Annotation[][] annotations) {
        List<Object> objList = new ArrayList<Object>();
        for(int i = 0; i < arguments.length; i++){
            if(annotations.length < i || annotations[i] == null){
                continue;
            }
            if(annotations[i].length == 0) {
                objList.add(arguments[i]);
            } else {
                boolean findIgnoreProfile = false;
                for(Annotation annotation : annotations[i]) {
                    if(annotation instanceof IgnoreProfile){
                        findIgnoreProfile = true;
                        break;
                    }
                }
                if(!findIgnoreProfile){
                    objList.add(arguments[i]);
                }
            }
        }
        return objList.toArray();
    }

    private boolean isFilterResult(Annotation[] annotations){
        if(annotations == null) {
            return false;
        }
        for(Annotation annotation : annotations) {
            if(annotation instanceof IgnoreResultProfile){
                return true;
            }
        }
        return false;
    }
}
