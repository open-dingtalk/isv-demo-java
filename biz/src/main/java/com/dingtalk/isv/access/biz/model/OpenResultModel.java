package com.dingtalk.isv.access.biz.model;

import java.io.Serializable;

/**
 * Created by nanlai on 2016/1/25.
 */
public class OpenResultModel implements Serializable {
    protected Long errcode;

    protected String errmsg;

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
