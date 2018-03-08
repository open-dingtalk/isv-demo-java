package com.dingtalk.isv.access.api.service;

import com.dingtalk.isv.access.api.model.LoginUserVO;
import com.dingtalk.isv.access.api.model.EmpVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

/**
 * 企业员工管理
 */
public interface EmpManageService {

    /**
     * 查询一个员工 UserId和corpId唯一确定个一个员工
     * @param userId    钉钉开放平台授权企业员工UserId
     * @param corpId    钉钉开放平台授权企业CorpId
     * @param suiteKey  套件SuiteKey
     * @return
     */
    ServiceResult<EmpVO> getEmpByUserId(String userId, String corpId, String suiteKey);

    /**
     * 获取当前登录用户
     * 接口用于企业或者ISV的H5页面免登
     * @param suitKey   套件SuiteKey
     * @param corpId    授权企业的CorpId
     * @param code      免登临时授权码。只能使用一次
     */
    ServiceResult<LoginUserVO> getEmpByAuthCode(String suitKey, String corpId, String code);

}
