package com.dingtalk.isv.access.api.service.suite;

import com.dingtalk.isv.access.api.model.SuiteTicketVO;
import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.SuiteVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

/**
 * 套件增删改查管理功能
 * 套件Ticket和Token管理功能
 * Created by 浩倡 on 16-1-17.
 */
public interface SuiteManageService{

    /**
     * 向DB中添加一个套件
     * @param suiteVO   suiteVO
     */
    ServiceResult<Void> addSuite(SuiteVO suiteVO);

    /**
     * 根据suiteKey查询套件
     * @param suiteKey  套件Key
     */
    ServiceResult<SuiteVO> getSuiteByKey(String suiteKey);

    /**
     * 根据suiteKey套件访问开放平台需要的suiteToken
     * 理论上一定会获取到可用的suiteToken,
     * 如果发现SuiteToken不存在或者即将过期,会实时去钉钉开放平台查询
     * @param suiteKey 套件Key
     */
    ServiceResult<SuiteTokenVO> getSuiteToken(String suiteKey);

    /**
     * 存储或者更新更新suiteTicket
     * 每当钉钉开放平台做推送的时候更新这个SuiteTicket
     * @param suiteTicketVO ticketvo
     */
    ServiceResult<Void> saveOrUpdateSuiteTicket(SuiteTicketVO suiteTicketVO);
}
