package com.dingtalk.isv.access.common.log.http;

/**
 * Created by mint on 16-1-27.
 */
public class HttpRequestContextHolder {

    private ThreadLocal<HttpRequestContext> httpRequestContextThreadLocal = new ThreadLocal<HttpRequestContext>();

    public HttpRequestContext getHttpRequestContext() {
        return httpRequestContextThreadLocal.get();
    }

    public void setHttpRequestContext(HttpRequestContext httpRequestContext) {
        this.httpRequestContextThreadLocal.set(httpRequestContext);
    }

    public void clean() {
        httpRequestContextThreadLocal.remove();
    }

}
