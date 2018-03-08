package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.biz.model.CorpTokenDO;

/**
 * Created by mint on 16-1-21.
 */
public class CorpTokenConverter {


    public static CorpTokenVO CorpTokenDO2CorpTokenVO(CorpTokenDO corpTokenDO){
        if(null==corpTokenDO){
            return null;
        }
        CorpTokenVO corpTokenVO = new CorpTokenVO();
        corpTokenVO.setId(corpTokenDO.getId());
        corpTokenVO.setGmtCreate(corpTokenDO.getGmtCreate());
        corpTokenVO.setGmtModified(corpTokenDO.getGmtModified());
        corpTokenVO.setCorpId(corpTokenDO.getCorpId());
        corpTokenVO.setSuiteKey(corpTokenDO.getSuiteKey());
        corpTokenVO.setCorpToken(corpTokenDO.getCorpToken());
        corpTokenVO.setExpiredTime(corpTokenDO.getExpiredTime());
        return corpTokenVO;
    }

    public static CorpTokenDO CorpTokenVO2CorpTokenDO(CorpTokenVO corpTokenVO){
        if(null==corpTokenVO){
            return null;
        }
        CorpTokenDO corpTokenDO = new CorpTokenDO();
        corpTokenDO.setId(corpTokenVO.getId());
        corpTokenDO.setGmtCreate(corpTokenVO.getGmtCreate());
        corpTokenDO.setGmtModified(corpTokenVO.getGmtModified());
        corpTokenDO.setCorpId(corpTokenVO.getCorpId());
        corpTokenDO.setSuiteKey(corpTokenVO.getSuiteKey());
        corpTokenDO.setCorpToken(corpTokenVO.getCorpToken());
        corpTokenDO.setExpiredTime(corpTokenVO.getExpiredTime());
        return corpTokenDO;
    }
}
