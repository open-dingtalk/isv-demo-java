package com.dingtalk.isv.access.api.model;

import java.io.Serializable;

/**
 * 企业授权套件事件
 * Created by lifeng.zlf on 2016/3/23.
 */
public class AuthChangeEvent implements Serializable {
    private String suiteToken;
    private String corpId;
    private String suiteKey;
    private String permanentCode;

    public String getSuiteToken() {
        return suiteToken;
    }

    public void setSuiteToken(String suiteToken) {
        this.suiteToken = suiteToken;
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

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    @Override
    public String toString() {
        return "CorpAuthSuiteEvent{" +
                "suiteToken='" + suiteToken + '\'' +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", permanentCode='" + permanentCode + '\'' +
                '}';
    }
}
