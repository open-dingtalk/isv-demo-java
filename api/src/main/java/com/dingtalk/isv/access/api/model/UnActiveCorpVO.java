package com.dingtalk.isv.access.api.model;

import java.io.Serializable;

/**
 * 授权流程中因为各种原因没有被激活的corpid
 * Created by mint on 16-10-30.
 */
public class UnActiveCorpVO implements Serializable{
    /**
     * 企业id
     */
    private String corpId;
    /**
     * 应用id
     */
    private Long appId;
    /**
     * 应用类型 0微应用,1服务窗。默认为0
     */
    private Integer appType=0;

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

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }
}
