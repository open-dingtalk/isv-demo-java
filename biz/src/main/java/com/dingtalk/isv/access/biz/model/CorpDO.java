package com.dingtalk.isv.access.biz.model;

import java.util.Date;

/**
 * 企业信息
 * Created by lifeng.zlf on 2016/1/20.
 */
public class CorpDO {
    /**
     * PK
     */
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    /**
     * 钉钉平台企业id
     */
    private String corpId;
    /**
     * 企业邀请码
     */
    private String inviteCode;
    /**
     * 企业所属行业
     */
    private String industry;
    /**
     *企业名称
     */
    private String corpName;
    /**
     * 企业邀请链接.该链接可以邀请其他人加入企业
     */
    private String inviteUrl;
    /**
     * 企业logo
     */
    private String corpLogoUrl;

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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public String getCorpLogoUrl() {
        return corpLogoUrl;
    }

    public void setCorpLogoUrl(String corpLogoUrl) {
        this.corpLogoUrl = corpLogoUrl;
    }

    @Override
    public String toString() {
        return "CorpDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", industry='" + industry + '\'' +
                ", corpName='" + corpName + '\'' +
                ", inviteUrl='" + inviteUrl + '\'' +
                ", corpLogoUrl='" + corpLogoUrl + '\'' +
                '}';
    }
}
