package com.dingtalk.isv.access.common.log.http;

/**
 * Created by mint on 16-1-27.
 */
public class HttpRequestContext {

    private Long startTime;
    private String clientIp;
    private String requestMethod;
    private String requestURI;
    private String requestURL;
    private String query;
    private String cookies;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }


    @Override
    public String toString() {
        return "HttpRequestContext{" +
                "startTime=" + startTime +
                ", clientIp='" + clientIp + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestURI='" + requestURI + '\'' +
                ", requestURL='" + requestURL + '\'' +
                ", query='" + query + '\'' +
                ", cookies='" + cookies + '\'' +
                '}';
    }
}
