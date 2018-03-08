package com.dingtalk.isv.access.api.model;

import java.io.Serializable;

/**
 * Created by jianzhi on 16-1-27.
 */
public class LoginUserVO implements Serializable {

    private String corpId;
    private String userId; //员工在企业内的UserID
    private Boolean isSys; //是否是管理员
    private Integer sysLevel; //级别，三种取值。0:非管理员 1：普通管理员 2：超级管理员
    private String deviceId; //手机设备号,由钉钉在安装时随机产生


    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsSys() {
        return isSys;
    }

    public void setIsSys(Boolean isSys) {
        this.isSys = isSys;
    }

    public Integer getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(Integer sysLevel) {
        this.sysLevel = sysLevel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
