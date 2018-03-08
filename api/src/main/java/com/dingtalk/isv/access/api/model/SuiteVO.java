package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 套件VO
 * Created by lifeng.zlf on 2016/1/15.
 */

public class SuiteVO implements Serializable{
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 套件名字
     */
    private String suiteName;

    /**
     * suite 的唯一key
     */
    private String suiteKey;

    /**
     * suite的唯一secrect，与key对应
     */
    private String suiteSecret;

    /**
     * 回调信息加解密参数
     */
    private String encodingAesKey;

    /**
     * 已填写用于生成签名和校验毁掉请求的合法性
     */
    private String token;

    /**
     * 回调地址
     */
    private String eventReceiveUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteSecret() {
        return suiteSecret;
    }

    public void setSuiteSecret(String suiteSecret) {
        this.suiteSecret = suiteSecret;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEventReceiveUrl() {
        return eventReceiveUrl;
    }

    public void setEventReceiveUrl(String eventReceiveUrl) {
        this.eventReceiveUrl = eventReceiveUrl;
    }

    @Override
    public String toString() {
        return "SuiteVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteName='" + suiteName + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteSecret='" + suiteSecret + '\'' +
                ", encodingAesKey='" + encodingAesKey + '\'' +
                ", token='" + token + '\'' +
                ", eventReceiveUrl='" + eventReceiveUrl + '\'' +
                '}';
    }
}
