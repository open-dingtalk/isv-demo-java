package com.dingtalk.isv.access.api.service.corp;

import com.dingtalk.isv.access.api.model.corp.DepartmentVO;
import com.dingtalk.isv.access.api.model.corp.StaffVO;
import com.dingtalk.isv.common.model.ServiceResult;

/**
 * Created by lifeng.zlf on 2016/3/16.
 */
public interface DeptManageService {
    /**
     * 获取部门
     * @param deptId
     * @param corpId
     * @param suiteKey
     * @return
     */
    public ServiceResult<DepartmentVO> getDept(Long deptId, String corpId, String suiteKey);
}
