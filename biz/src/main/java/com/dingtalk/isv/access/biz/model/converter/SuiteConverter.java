package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.SuiteVO;
import com.dingtalk.isv.access.biz.model.SuiteDO;

/**
 * Created by lifeng.zlf on 2016/1/15.
 */

public class SuiteConverter {
   public static SuiteVO SuiteDO2SuiteVO(SuiteDO suiteDO){
       if(null==suiteDO){
            return null;
       }
       SuiteVO suiteVO = new SuiteVO();
       suiteVO.setId(suiteDO.getId());
       suiteVO.setSuiteName(suiteDO.getSuiteName());
       suiteVO.setSuiteKey(suiteDO.getSuiteKey());
       suiteVO.setSuiteSecret(suiteDO.getSuiteSecret());
       suiteVO.setEncodingAesKey(suiteDO.getEncodingAesKey());
       suiteVO.setToken(suiteDO.getToken());
       suiteVO.setEventReceiveUrl(suiteDO.getEventReceiveUrl());
       suiteVO.setGmtCreate(suiteDO.getGmtCreate());
       suiteVO.setGmtModified(suiteDO.getGmtModified());
       return suiteVO;
   }

    public static SuiteDO SuiteVO2SuiteDO(SuiteVO suiteVO){
        if(null==suiteVO){
            return null;
        }
        SuiteDO suiteDO = new SuiteDO();
        suiteDO.setId(suiteVO.getId());
        suiteDO.setSuiteName(suiteVO.getSuiteName());
        suiteDO.setSuiteKey(suiteVO.getSuiteKey());
        suiteDO.setSuiteSecret(suiteVO.getSuiteSecret());
        suiteDO.setEncodingAesKey(suiteVO.getEncodingAesKey());
        suiteDO.setToken(suiteVO.getToken());
        suiteDO.setEventReceiveUrl(suiteVO.getEventReceiveUrl());
        suiteDO.setGmtCreate(suiteVO.getGmtCreate());
        suiteDO.setGmtModified(suiteVO.getGmtModified());
        return suiteDO;
    }
}
