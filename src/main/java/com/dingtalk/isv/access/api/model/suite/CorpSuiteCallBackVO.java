package com.dingtalk.isv.access.api.model.suite;

import java.util.Date;
import java.util.List;

/**
 * 注册监听企业发生变更的回调信息模型
 * Created by 浩倡 on 16-1-17.
 */
public class CorpSuiteCallBackVO {
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

    /**
     * 回调tag的json字符串
     */
    private List<String> callbackTagList;

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

    public List<String> getCallbackTagList() {
        return callbackTagList;
    }

    public void setCallbackTagList(List<String> callbackTagList) {
        this.callbackTagList = callbackTagList;
    }

    @Override
    public String toString() {
        return "CorpSuiteCallBackVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", callbackTagList=" + callbackTagList +
                '}';
    }
}
