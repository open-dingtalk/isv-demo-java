package com.dingtalk.isv.access.api.model;

import java.io.Serializable;

/**
 * Created by jianzhi on 16-1-27.
 */
public class OALoginUserVO implements Serializable {
    //OA后台跳转过来的企业CorpId
    private String corpId;
    private String corpName;
    private String userId;
    private String name;
    private String email;
    private String avatar;
    private Boolean isSys; //是否是管理员

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getSys() {
        return isSys;
    }

    public void setSys(Boolean sys) {
        isSys = sys;
    }
}
