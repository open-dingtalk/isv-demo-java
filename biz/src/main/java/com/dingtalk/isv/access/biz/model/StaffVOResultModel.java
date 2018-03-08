package com.dingtalk.isv.access.biz.model;


import com.dingtalk.isv.access.api.model.EmpVO;

import java.util.List;

/**
 * Created by nanlai on 2016/1/25.
 */
public class StaffVOResultModel extends OpenResultModel{
    private List<EmpVO> userlist;

    public List<EmpVO> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<EmpVO> userlist) {
        this.userlist = userlist;
    }
}
