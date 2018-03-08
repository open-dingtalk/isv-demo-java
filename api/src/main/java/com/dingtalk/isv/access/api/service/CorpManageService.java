package com.dingtalk.isv.access.api.service;


import com.dingtalk.isv.access.api.model.*;
import com.dingtalk.isv.access.api.model.CorpChannelAppVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

/**
 * 企业增删改查管理功能
 * 企业访问开放平台Token管理功能
 * Created by 浩倡 on 16-1-17.
 */
public interface CorpManageService {
    /**
     * 增加或者修改一个企业信息
     * @param corpVO
     * @return
     */
    ServiceResult<Void> saveOrUpdateCorp(CorpVO corpVO);

    /**
     * 获取授权企业访问开放平台的AccessToken
     * @param suiteKey  套件的SuiteKey
     * @param corpId    授权企业的CorpId
     */
    ServiceResult<CorpTokenVO> getCorpToken(String suiteKey, String corpId);

    /**
     * 获取企业访问开放平台服务窗token
     * @param suiteKey
     * @param corpId
     * @return
     */
    ServiceResult<CorpChannelTokenVO> getCorpChannelToken(String suiteKey, String corpId);

    /**
     * 删除企业token
     * @param suiteKey
     * @param corpId
     * @return
     */
    ServiceResult<CorpTokenVO> deleteCorpToken(String suiteKey,String corpId);

    /**
     * 删除企业服务窗token
     * @param suiteKey
     * @param corpId
     * @return
     */
    ServiceResult<CorpTokenVO> deleteCorpChannelToken(String suiteKey,String corpId);


    /**
     * 获取企业访问开放平台js ticket
     * @return
     */
    ServiceResult<CorpJSAPITicketVO> getCorpJSAPITicket(String suiteKey, String corpId);


    /**
     * 获取企业访问开放平台js ticket
     * @return
     */
    ServiceResult<CorpChannelJSAPITicketVO> getCorpChannelJSAPITicket(String suiteKey, String corpId);


    /**
     * 获取企业开通的套件下的微应用
     * @param corpId
     * @return
     */
    ServiceResult<CorpAppVO>  getCorpApp(String corpId, Long appId);


    /**
     * 获取企业开通的套件下的服务窗应用
     * @param corpId
     * @return
     */
    ServiceResult<CorpChannelAppVO>  getCorpChannelApp(String corpId, Long appId);

}
