package com.dingtalk.isv.access.biz.model;

import java.util.List;

/**
 * Created by nanlai on 2016/1/25.
 */
public class StaffListResultModel extends OpenResultModel{
    private List<String> userlist;

    public List<String> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<String> userlist) {
        this.userlist = userlist;
    }
}
