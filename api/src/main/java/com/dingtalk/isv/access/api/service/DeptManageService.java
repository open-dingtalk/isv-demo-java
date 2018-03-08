package com.dingtalk.isv.access.api.service;

import com.dingtalk.isv.access.api.model.DingDepartmentVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

/**
 * 企业部门管理相关的服务
 */
public interface DeptManageService {
    /**
     * 获取部门详情
     * @param deptId 部门ID
     * @param corpId    授权企业ID
     * @param suiteKey
     * @return
     */
    ServiceResult<DingDepartmentVO> getDept(Long deptId, String corpId, String suiteKey);

    /**
     * 从钉钉拉取部门详情.存储到DB
     * @param suiteKey  套件SuiteKey
     * @param corpId    授权企业CorpId
     * @param deptId    部门ID
     */
    ServiceResult<DingDepartmentVO> saveDept(String suiteKey, String corpId, Long deptId);

}
