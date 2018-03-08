package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.Date;

public class ISVSSOTokenVO implements Serializable{
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
    private String isvCorpId;

    /**
     * ISV在OA后台免登的token
     */
    private String isvSsoToken;

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

    public String getIsvCorpId() {
        return isvCorpId;
    }

    public void setIsvCorpId(String isvCorpId) {
        this.isvCorpId = isvCorpId;
    }

    public String getIsvSsoToken() {
        return isvSsoToken;
    }

    public void setIsvSsoToken(String isvSsoToken) {
        this.isvSsoToken = isvSsoToken;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
