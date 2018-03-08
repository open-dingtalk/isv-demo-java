package com.dingtalk.isv.access.biz.model;

import java.util.Date;

/**
 * 企业访问开放平台js tickets信息
 * Created by lifeng.zlf on 2016/1/20.
 */
public class CorpChannelJSAPITicketDO {

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
     * 套件key
     */
    private String suiteKey;

    /**
     * 钉钉平台企业ID
     */
    private String corpId;

    /**
     * 企业jsTicket
     */
    private String corpChannelJSAPITicket;

    /**
     * 过期时间
     */
    private Date expiredTime;

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

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpChannelJSAPITicket() {
        return corpChannelJSAPITicket;
    }

    public void setCorpChannelJSAPITicket(String corpChannelJSAPITicket) {
        this.corpChannelJSAPITicket = corpChannelJSAPITicket;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }


    @Override
    public String toString() {
        return "CorpChannelJSAPITicketDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", corpChannelJSAPITicket='" + corpChannelJSAPITicket + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
