package com.dingtalk.isv.access.api.model.org;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业访问开放平台js tickets信息
 * Created by lifeng.zlf on 2016/1/20.
 */
public class OrgChannelJSAPITicketVO implements Serializable {

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


    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }


    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }


    public String getCorpChannelJSAPITicket() {
        return corpChannelJSAPITicket;
    }

    public void setCorpChannelJSAPITicket(String corpChannelJSAPITicket) {
        this.corpChannelJSAPITicket = corpChannelJSAPITicket;
    }

    @Override
    public String toString() {
        return "OrgChannelJSAPITicketVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", corpChannelJSAPITicket='" + corpChannelJSAPITicket + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
