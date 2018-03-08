package com.dingtalk.isv.access.api.service.suite;

import com.dingtalk.isv.access.api.model.CorpAppVO;
import com.dingtalk.isv.access.api.model.CorpSuiteAuthVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

import java.util.List;

/**
 * 企业对套件授权service
 *
 */
public interface CorpSuiteAuthService {
    /**
     * 获取企业授权信息
     *
     * @param corpId
     * @param suiteKey
     * @return
     */
    ServiceResult<CorpSuiteAuthVO> getCorpSuiteAuth(String corpId, String suiteKey);

    /**
     * 用临时授权码换取企业授权信息
     *
     * @param suiteKey
     * @param tmpAuthCode
     * @return
     */
    ServiceResult<CorpSuiteAuthVO> saveOrUpdateCorpSuiteAuth(String suiteKey, String tmpAuthCode);

    /**
     * 解除授权
     *
     * @param corpId
     * @param suiteKey
     * @return
     */
    ServiceResult<Void> deleteCorpSuiteAuth(String corpId, String suiteKey);

    /**
     * 删除企业为应用信息
     *
     * @param corpId
     * @param appId
     * @return
     */
    ServiceResult<CorpAppVO> getCorpApp(String corpId, Long appId);

    /**
     * 删除企业为应用信息
     *
     * @param corpId
     * @param appId
     * @return
     */
    ServiceResult<Void> deleteCorpApp(String corpId, Long appId);

    /**
     * 处理权限变更事件
     *
     * @param suiteKey
     * @param corpId
     * @return
     */
    ServiceResult<Void> handleChangeAuth(String suiteKey, String corpId);


    /**
     * 处理解除授权事件
     *
     * @param suiteKey
     * @param corpId
     * @return
     */
    ServiceResult<Void> handleRelieveAuth(String suiteKey, String corpId);

    /**
     * 分页查询套件授权的所有企业
     *
     * @param startRow
     * @param pageSize
     * @return
     */
    ServiceResult<List<CorpSuiteAuthVO>> getCorpSuiteAuthByPage(String suiteKey, int startRow, int pageSize);
}
