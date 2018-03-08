package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifeng.zlf on 2016/3/28.
 */
public abstract class AbstractCorpCallBackVO implements Serializable{
    private String callBackTag;
    private String suiteKey;
    private String corpId;
    private Date date;
    public String getCallBackTag() {
        return callBackTag;
    }

    public void setCallBackTag(String callBackTag) {
        this.callBackTag = callBackTag;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
