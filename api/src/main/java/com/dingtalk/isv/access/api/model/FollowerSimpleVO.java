package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by mint on 16-1-22.
 */
public class FollowerSimpleVO implements Serializable {
   private String openid;
   private String unionid;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "FollowerSimpleVO{" +
                "openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
