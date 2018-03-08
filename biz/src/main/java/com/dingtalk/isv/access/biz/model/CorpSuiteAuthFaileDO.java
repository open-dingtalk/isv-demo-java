package com.dingtalk.isv.access.biz.model;


import com.dingtalk.isv.access.api.enums.suite.AuthFaileType;
import com.dingtalk.isv.access.api.enums.suite.SuitePushType;

import java.util.Date;

/**
 * Created by mint on 16-1-26.
 */
public class CorpSuiteAuthFaileDO {

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
     * 企业corpid
     */
    private String corpId;

    /**
     * 套件key
     */
    private String suiteKey;



    private AuthFaileType authFaileType;


    private String faileInfo;

    private SuitePushType suitePushType;

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

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public AuthFaileType getAuthFaileType() {
        return authFaileType;
    }

    public void setAuthFaileType(AuthFaileType authFaileType) {
        this.authFaileType = authFaileType;
    }

    public String getFaileInfo() {
        return faileInfo;
    }

    public void setFaileInfo(String faileInfo) {
        this.faileInfo = faileInfo;
    }

    public SuitePushType getSuitePushType() {
        return suitePushType;
    }

    public void setSuitePushType(SuitePushType suitePushType) {
        this.suitePushType = suitePushType;
    }
}
