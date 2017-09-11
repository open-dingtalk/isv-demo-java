package com.dingtalk.isv.access.biz.corp.model;


import com.dingtalk.isv.access.api.model.corp.StaffVO;

import java.util.List;

/**
 * Created by nanlai on 2016/1/25.
 */
public class StaffVOResultModel extends OpenResultModel{
    private List<StaffVO> userlist;

    public List<StaffVO> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<StaffVO> userlist) {
        this.userlist = userlist;
    }
}
