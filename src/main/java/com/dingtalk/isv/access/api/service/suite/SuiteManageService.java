package com.dingtalk.isv.access.api.service.suite;

import com.dingtalk.isv.access.api.model.suite.SuiteTicketVO;
import com.dingtalk.isv.access.api.model.suite.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.common.model.ServiceResult;

import java.util.List;

/**
 * 套件增删改查管理功能
 * 套件Ticket和Token管理功能
 * Created by 浩倡 on 16-1-17.
 */
public interface SuiteManageService{

    /**
     * 获取所有suiteKey
     * @return
     */
    public ServiceResult<List<SuiteVO>> getAllSuite();

    /**
     * 创建一个套件
     * @param suiteVO         suiteVO
     */
    public ServiceResult<Void> addSuite(SuiteVO suiteVO);

    /**
     * 根据suiteKey查询套件
     * @param suiteKey
     * @return
     */
    public ServiceResult<SuiteVO> getSuiteByKey(String suiteKey);

    /**
     * 存储或者更新更新suiteToken
     * @return
     */
    public ServiceResult<Void> saveOrUpdateSuiteToken(String suiteKey);

    /**
     * 根据suiteKey套件访问开放平台需要的suiteToken
     * @param suiteKey
     * @return
     */
    public ServiceResult<SuiteTokenVO> getSuiteToken(String suiteKey);

    /**
     * 获取所有suiteToken
     * @return
     */
    public ServiceResult<List<SuiteTokenVO>> getAllSuiteToken();

    /**
     * 存储或者更新更新suiteTicket
     * @param suiteTicketVO
     * @return
     */
    public ServiceResult<Void> saveOrUpdateSuiteTicket(SuiteTicketVO suiteTicketVO);

    /**
     * 查询所有suiteTicket
     * @return
     */
    public ServiceResult<List<SuiteTicketVO>> getAllSuiteTicket();

    /**
     * 根据suiteKey查询suiteTicket
     * @param suiteKey
     * @return
     */
    public ServiceResult<SuiteTicketVO> getSuiteTicket(String suiteKey);
}
