package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业使用微应用信息
 * Created by 浩倡 on 16-1-17.
 */
public class CorpChannelAppVO implements Serializable {
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
     * 钉钉平台企业id
     */
    private String corpId;
    /**
     * 微应用原始ID
     */
    private Long appId;
    /**
     * 企业使用微应用的id
     */
    private Long agentId;
    /**
     * 微应用名称
     */
    private String agentName;
    /**
     * 微应用logo
     */
    private String logoUrl;


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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    @Override
    public String toString() {
        return "CorpChannelAppDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", appId=" + appId +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
