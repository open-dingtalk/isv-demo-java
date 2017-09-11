package com.dingtalk.isv.access.api.service.org;

import com.dingtalk.isv.access.api.model.corp.CorpChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.corp.CorpChannelTokenVO;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.org.OrgChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgChannelTokenVO;
import com.dingtalk.isv.access.api.model.org.OrgJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgTokenVO;
import com.dingtalk.isv.common.model.ServiceResult;

/**
 * 企业自身接口,和isv无关
 * Created by mint on 16-8-31.
 */
public interface OrgManangerService {

    /**
     * 获取企业访问开放平台js ticket
     * @return
     */
    public ServiceResult<OrgJSAPITicketVO> getOrgJSAPITicket(String corpId,String corpToken);


    /**
     * 获取企业访问开放平台js ticket
     * @return
     */
    public ServiceResult<OrgChannelJSAPITicketVO> getOrgChannelJSAPITicket(String corpId, String corpChannelSecret);



    /**
     * 获取企业访问开放平台token
     * @param corpSecret
     * @param corpId
     * @return
     */
    public ServiceResult<OrgTokenVO> getOrgToken(String corpId,String corpSecret);

    /**
     * 获取企业访问开放平台服务窗token
     * @param corpChannelSecret
     * @param corpId
     * @return
     */
    public ServiceResult<OrgChannelTokenVO> getOrgChannelToken(String corpId, String corpChannelSecret);
}
