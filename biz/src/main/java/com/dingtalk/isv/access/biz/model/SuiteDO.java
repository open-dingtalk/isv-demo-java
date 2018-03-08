package com.dingtalk.isv.access.biz.model;

import java.util.Date;

/**
 * Created by lifeng.zlf on 2016/1/15.
 */

public class SuiteDO {
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

    /**
     * setter for column 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for column 主键
     */
    public Long getId() {
        return this.id;
    }

    /**
     * setter for column 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * getter for column 创建时间
     */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /**
     * setter for column 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * getter for column 修改时间
     */
    public Date getGmtModified() {
        return this.gmtModified;
    }

    /**
     * setter for column 套件名字
     */
    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    /**
     * getter for column 套件名字
     */
    public String getSuiteName() {
        return this.suiteName;
    }

    /**
     * setter for column suite 的唯一key
     */
    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    /**
     * getter for column suite 的唯一key
     */
    public String getSuiteKey() {
        return this.suiteKey;
    }

    /**
     * setter for column suite的唯一secrect，与key对应
     */
    public void setSuiteSecret(String suiteSecret) {
        this.suiteSecret = suiteSecret;
    }

    /**
     * getter for column suite的唯一secrect，与key对应
     */
    public String getSuiteSecret() {
        return this.suiteSecret;
    }

    /**
     * setter for column 回调信息加解密参数
     */
    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    /**
     * getter for column 回调信息加解密参数
     */
    public String getEncodingAesKey() {
        return this.encodingAesKey;
    }

    /**
     * setter for column 已填写用于生成签名和校验毁掉请求的合法性
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * getter for column 已填写用于生成签名和校验毁掉请求的合法性
     */
    public String getToken() {
        return this.token;
    }

    /**
     * setter for column 回调地址
     */
    public void setEventReceiveUrl(String eventReceiveUrl) {
        this.eventReceiveUrl = eventReceiveUrl;
    }

    /**
     * getter for column 回调地址
     */
    public String getEventReceiveUrl() {
        return this.eventReceiveUrl;
    }

    @Override
    public String toString() {
        return "SuiteDO{" +
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
