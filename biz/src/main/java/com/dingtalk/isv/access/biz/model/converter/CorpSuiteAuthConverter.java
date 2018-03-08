package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.CorpSuiteAuthVO;
import com.dingtalk.isv.access.biz.model.CorpSuiteAuthDO;

/**
 * Created by lifeng.zlf on 2016/1/19.
 */
public class CorpSuiteAuthConverter {

    public static CorpSuiteAuthVO CorpSuiteAuthDO2CorpSuiteAuthVO(CorpSuiteAuthDO corpSuiteAuthDO){
        if(null==corpSuiteAuthDO){
            return null;
        }
        CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
        corpSuiteAuthVO.setId(corpSuiteAuthDO.getId());
        corpSuiteAuthVO.setGmtCreate(corpSuiteAuthDO.getGmtCreate());
        corpSuiteAuthVO.setGmtModified(corpSuiteAuthDO.getGmtModified());
        corpSuiteAuthVO.setSuiteKey(corpSuiteAuthDO.getSuiteKey());
        corpSuiteAuthVO.setCorpId(corpSuiteAuthDO.getCorpId());
        corpSuiteAuthVO.setPermanentCode(corpSuiteAuthDO.getPermanentCode());
        corpSuiteAuthVO.setChPermanentCode(corpSuiteAuthDO.getChPermanentCode());
        return corpSuiteAuthVO;
    }


    public static CorpSuiteAuthDO CorpSuiteAuthVO2CorpSuiteAuthDO(CorpSuiteAuthVO corpSuiteAuthVO){
        if(null==corpSuiteAuthVO){
            return null;
        }
        CorpSuiteAuthDO corpSuiteAuthDO = new CorpSuiteAuthDO();
        corpSuiteAuthDO.setId(corpSuiteAuthVO.getId());
        corpSuiteAuthDO.setGmtCreate(corpSuiteAuthVO.getGmtCreate());
        corpSuiteAuthDO.setGmtModified(corpSuiteAuthVO.getGmtModified());
        corpSuiteAuthDO.setSuiteKey(corpSuiteAuthVO.getSuiteKey());
        corpSuiteAuthDO.setCorpId(corpSuiteAuthVO.getCorpId());
        corpSuiteAuthDO.setPermanentCode(corpSuiteAuthVO.getPermanentCode());
        corpSuiteAuthDO.setChPermanentCode(corpSuiteAuthVO.getChPermanentCode());
        return corpSuiteAuthDO;
    }
}
