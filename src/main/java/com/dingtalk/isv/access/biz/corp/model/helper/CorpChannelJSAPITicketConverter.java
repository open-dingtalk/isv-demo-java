package com.dingtalk.isv.access.biz.corp.model.helper;

import com.dingtalk.isv.access.api.model.corp.CorpChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.corp.CorpJSAPITicketVO;
import com.dingtalk.isv.access.biz.corp.model.CorpChannelJSAPITicketDO;
import com.dingtalk.isv.access.biz.corp.model.CorpJSAPITicketDO;

/**
 * Created by mint on 16-1-21.
 */
public class CorpChannelJSAPITicketConverter {


    public static CorpChannelJSAPITicketVO corpChannelJSAPITicketDO2CorpChannelJSAPITicketVO(CorpChannelJSAPITicketDO corpChannelJSAPITicketDO){
        if(null==corpChannelJSAPITicketDO){
            return null;
        }
        CorpChannelJSAPITicketVO corpChannelJSAPITicketVO = new CorpChannelJSAPITicketVO();
        corpChannelJSAPITicketVO.setId(corpChannelJSAPITicketDO.getId());
        corpChannelJSAPITicketVO.setGmtCreate(corpChannelJSAPITicketDO.getGmtCreate());
        corpChannelJSAPITicketVO.setGmtModified(corpChannelJSAPITicketDO.getGmtModified());
        corpChannelJSAPITicketVO.setCorpId(corpChannelJSAPITicketDO.getCorpId());
        corpChannelJSAPITicketVO.setSuiteKey(corpChannelJSAPITicketDO.getSuiteKey());
        corpChannelJSAPITicketVO.setCorpChannelJSAPITicket(corpChannelJSAPITicketDO.getCorpChannelJSAPITicket());
        corpChannelJSAPITicketVO.setExpiredTime(corpChannelJSAPITicketDO.getExpiredTime());
        return corpChannelJSAPITicketVO;
    }

    public static CorpChannelJSAPITicketDO corpChannelJSAPITicketVO2CorpChannelJSAPITicketDO(CorpChannelJSAPITicketVO corpChannelJSAPITicketVO){
        if(null==corpChannelJSAPITicketVO){
            return null;
        }
        CorpChannelJSAPITicketDO corpChannelJSAPITicketDO = new CorpChannelJSAPITicketDO();
        corpChannelJSAPITicketDO.setId(corpChannelJSAPITicketVO.getId());
        corpChannelJSAPITicketDO.setGmtCreate(corpChannelJSAPITicketVO.getGmtCreate());
        corpChannelJSAPITicketDO.setGmtModified(corpChannelJSAPITicketVO.getGmtModified());
        corpChannelJSAPITicketDO.setCorpId(corpChannelJSAPITicketVO.getCorpId());
        corpChannelJSAPITicketDO.setSuiteKey(corpChannelJSAPITicketVO.getSuiteKey());
        corpChannelJSAPITicketDO.setCorpChannelJSAPITicket(corpChannelJSAPITicketVO.getCorpChannelJSAPITicket());
        corpChannelJSAPITicketDO.setExpiredTime(corpChannelJSAPITicketVO.getExpiredTime());
        return corpChannelJSAPITicketDO;
    }
}
