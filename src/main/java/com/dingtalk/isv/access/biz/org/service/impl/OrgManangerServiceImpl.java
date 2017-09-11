package com.dingtalk.isv.access.biz.org.service.impl;

import com.dingtalk.isv.access.api.model.org.OrgChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgChannelTokenVO;
import com.dingtalk.isv.access.api.model.org.OrgJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgTokenVO;
import com.dingtalk.isv.access.api.service.org.OrgManangerService;
import com.dingtalk.isv.access.biz.dingutil.OrgOapiRequestHelper;
import com.dingtalk.isv.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by mint on 16-8-31.
 */
public class OrgManangerServiceImpl implements OrgManangerService {
    private static final Logger bizLogger = LoggerFactory.getLogger("ORG_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(OrgManangerServiceImpl.class);
    @Resource
    private OrgOapiRequestHelper orgOapiRequestHelper;
    @Override
    public ServiceResult<OrgJSAPITicketVO> getOrgJSAPITicket(String corpId, String corpToken) {
        ServiceResult<OrgJSAPITicketVO> sr = orgOapiRequestHelper.getOrgJSAPITicket(corpId, corpToken);
        return sr;
    }

    @Override
    public ServiceResult<OrgChannelJSAPITicketVO> getOrgChannelJSAPITicket(String corpId, String corpChannelSecret) {
        return null;
    }

    @Override
    public ServiceResult<OrgTokenVO> getOrgToken(String corpId, String corpSecret) {
        //TODO 要存储起来的,没时间暂时不搞了
        ServiceResult<OrgTokenVO> sr = orgOapiRequestHelper.getOrgToken(corpId,corpSecret);
        return sr;
    }

    @Override
    public ServiceResult<OrgChannelTokenVO> getOrgChannelToken(String corpId, String corpChannelSecret) {
        return orgOapiRequestHelper.getOrgChannelToken(corpId, corpChannelSecret);
    }
}
