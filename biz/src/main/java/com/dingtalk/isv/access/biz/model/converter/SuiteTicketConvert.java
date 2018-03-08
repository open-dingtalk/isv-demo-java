package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.SuiteTicketVO;
import com.dingtalk.isv.access.biz.model.SuiteTicketDO;

/**
 * Created by lifeng.zlf on 2016/1/19.
 */
public class SuiteTicketConvert {

    public static SuiteTicketVO suiteTicketDO2SuiteTicketVO(SuiteTicketDO suiteTicketDO){
        if(null==suiteTicketDO){
            return null;
        }
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setId(suiteTicketDO.getId());
        suiteTicketVO.setGmtCreate(suiteTicketDO.getGmtCreate());
        suiteTicketVO.setGmtModified(suiteTicketDO.getGmtModified());
        suiteTicketVO.setSuiteKey(suiteTicketDO.getSuiteKey());
        suiteTicketVO.setSuiteTicket(suiteTicketDO.getSuiteTicket());
        return suiteTicketVO;
    }


    public static SuiteTicketDO suiteTicketVO2SuiteTicketDO(SuiteTicketVO suiteTicketVO){
        if(null==suiteTicketVO){
            return null;
        }
        SuiteTicketDO suiteTicketDO = new SuiteTicketDO();
        suiteTicketDO.setId(suiteTicketVO.getId());
        suiteTicketDO.setGmtCreate(suiteTicketVO.getGmtCreate());
        suiteTicketDO.setGmtModified(suiteTicketVO.getGmtModified());
        suiteTicketDO.setSuiteKey(suiteTicketVO.getSuiteKey());
        suiteTicketDO.setSuiteTicket(suiteTicketVO.getSuiteTicket());
        return suiteTicketDO;
    }
}
