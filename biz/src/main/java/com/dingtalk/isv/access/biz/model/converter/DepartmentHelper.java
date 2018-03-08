package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.DingDepartmentVO;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

/**
 * Created by mint on 16-7-17.
 */
public class DepartmentHelper {

    public static DingDepartmentVO DepartmentDetail2DepartmentVO(DepartmentDetail departmentDetail){
        if(null==departmentDetail){
            return null;
        }
        DingDepartmentVO departmentVO = new DingDepartmentVO();

        return departmentVO;
    }

}
