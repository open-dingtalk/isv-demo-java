package com.dingtalk.isv.access.web.common;

/**系统配置
 * 
 */
public class SystemConfig{
    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getEnvironmentUrl() {
        return environmentUrl;
    }

    public void setEnvironmentUrl(String environmentUrl) {
        this.environmentUrl = environmentUrl;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getMicroappAppId() {
        return microappAppId;
    }

    public void setMicroappAppId(String microappAppId) {
        this.microappAppId = microappAppId;
    }

    /**
    
     * 回调地址
     */
    private String callback;

    /**
     * 访问钉钉的服务端地址
     */
    private String environmentUrl;

    /**
     * 环境
     */
    private String env;

    /**
     * 套件key
     */
    private String suiteKey;

    /**
     * 应用ID
     */
    private String microappAppId;

}
