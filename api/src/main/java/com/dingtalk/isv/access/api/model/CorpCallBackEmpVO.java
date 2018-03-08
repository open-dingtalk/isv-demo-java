package com.dingtalk.isv.access.api.model;

import java.util.List;

/**
 * 员工相关的callback
 * Created by lifeng.zlf on 2016/3/28.
 */
public class CorpCallBackEmpVO extends AbstractCorpCallBackVO {
    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
