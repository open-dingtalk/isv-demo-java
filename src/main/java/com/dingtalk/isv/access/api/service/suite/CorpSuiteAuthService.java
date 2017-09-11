package com.dingtalk.isv.access.api.service.suite;

import com.dingtalk.isv.access.api.model.corp.CorpAppVO;
import com.dingtalk.isv.access.api.model.corp.callback.CorpChannelAppVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteCallBackVO;
import com.dingtalk.isv.common.model.ServiceResult;

import java.util.List;

/**
 * 企业对套件授权service
 */
public interface CorpSuiteAuthService {
    /**
     * 获取企业授权信息
     *
     * @param corpId
     * @param suiteKey
     * @return
     */
    public ServiceResult<CorpSuiteAuthVO> getCorpSuiteAuth(String corpId, String suiteKey);

    /**
     * 存储或更新企业授权信息
     *
     * @param corpSuiteAuthVO
     * @return
     */
    public ServiceResult<Void> saveOrUpdateCorpSuiteAuth(CorpSuiteAuthVO corpSuiteAuthVO);

    /**
     * 用临时授权码换取企业授权信息
     *
     * @param suiteKey
     * @param tmpAuthCode
     * @return
     */
    public ServiceResult<CorpSuiteAuthVO> saveOrUpdateCorpSuiteAuth(String suiteKey, String tmpAuthCode);

    /**
     * 调用钉钉平台方法激活企业app
     *
     * @param suiteKey
     * @param corpId
     * @return
     */
    public ServiceResult<Void> activeCorpApp(String suiteKey, String corpId,String permanentCode);

    /**
     * 同步企业信息
     * @param suiteToken
     * @param suiteKey
     * @param corpId
     * @param permanentCode
     * @return
     */
    public ServiceResult<Void> getCorpInfo(String suiteToken, String suiteKey, String corpId, String permanentCode);

    /**
     * 解除授权
     *
     * @param corpId
     * @param suiteKey
     * @return
     */
    public ServiceResult<Void> deleteCorpSuiteAuth(String corpId, String suiteKey);

    /**
     * @param corpAppVO
     * @return
     */
    public ServiceResult<Void> saveOrUpdateCorpApp(CorpAppVO corpAppVO);

    /**
     * 更新企服务窗应用
     * @param corpChannelAppVO
     * @return
     */
    public ServiceResult<Void> saveOrUpdateCorpChannelApp(CorpChannelAppVO corpChannelAppVO);

    /**
     * 删除企业为应用信息
     *
     * @param corpId
     * @param appId
     * @return
     */
    public ServiceResult<CorpAppVO> getCorpApp(String corpId, Long appId);

    /**
     * 删除企业为应用信息
     *
     * @param corpId
     * @param appId
     * @return
     */
    public ServiceResult<Void> deleteCorpApp(String corpId, Long appId);

    /**
     * 处理权限变更事件
     *
     * @param suiteKey
     * @param corpId
     * @return
     */
    public ServiceResult<Void> handleChangeAuth(String suiteKey, String corpId);


    /**
     * 处理解除授权事件
     *
     * @param suiteKey
     * @param corpId
     * @return
     */
    public ServiceResult<Void> handleRelieveAuth(String suiteKey, String corpId);

    /**
     * 分页查询套件授权的所有企业
     *
     * @param startRow
     * @param pageSize
     * @return
     */
    public ServiceResult<List<CorpSuiteAuthVO>> getCorpSuiteAuthByPage(String suiteKey, int startRow, int pageSize);

    /**
     * 查询企业注册的回调地址
     * @param suiteKey
     * @param corpId
     * @return
     */
    public ServiceResult<CorpSuiteCallBackVO> getCorpCallback(String suiteKey, String corpId);

    /**
     * 注册回调地址
     * @param suiteKey
     * @param corpId
     * @param callBakUrl
     * @param tagList
     * @return
     */
    public ServiceResult<Void> saveCorpCallback(String suiteKey, String corpId,String callBakUrl, List<String> tagList);

    /**
     * 更新回调地址
     * @param suiteKey
     * @param corpId
     * @param callBakUrl
     * @param tagList
     * @return
     */
    public ServiceResult<Void> updateCorpCallback(String suiteKey, String corpId,String callBakUrl, List<String> tagList);







}
