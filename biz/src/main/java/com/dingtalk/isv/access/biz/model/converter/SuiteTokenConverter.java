package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.biz.model.SuiteTokenDO;

/**
 * Created by lifeng.zlf on 2016/1/19.
 */
public class SuiteTokenConverter {

    public static SuiteTokenVO suiteTokenDO2SuiteTokenVO(SuiteTokenDO suiteTokenDO){
        if(null==suiteTokenDO){
            return null;
        }
        SuiteTokenVO suiteTokenVO = new SuiteTokenVO();
        suiteTokenVO.setId(suiteTokenDO.getId());
        suiteTokenVO.setGmtCreate(suiteTokenDO.getGmtCreate());
        suiteTokenVO.setGmtModified(suiteTokenDO.getGmtModified());
        suiteTokenVO.setSuiteKey(suiteTokenDO.getSuiteKey());
        suiteTokenVO.setExpiredTime(suiteTokenDO.getExpiredTime());
        suiteTokenVO.setSuiteToken(suiteTokenDO.getSuiteToken());
        return suiteTokenVO;
    }

    public static SuiteTokenDO suiteTokenVO2SuiteTokenDO(SuiteTokenVO suiteTokenVO){
        if(null==suiteTokenVO){
            return null;
        }
        SuiteTokenDO suiteTokenDO = new SuiteTokenDO();
        suiteTokenDO.setId(suiteTokenVO.getId());
        suiteTokenDO.setGmtCreate(suiteTokenVO.getGmtCreate());
        suiteTokenDO.setGmtModified(suiteTokenVO.getGmtModified());
        suiteTokenDO.setSuiteKey(suiteTokenVO.getSuiteKey());
        suiteTokenDO.setExpiredTime(suiteTokenVO.getExpiredTime());
        suiteTokenDO.setSuiteToken(suiteTokenVO.getSuiteToken());
        return suiteTokenDO;
    }



}
