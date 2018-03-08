package com.dingtalk.isv.access.biz.model.converter;


import com.dingtalk.isv.access.api.model.CorpChannelTokenVO;
import com.dingtalk.isv.access.biz.model.CorpChannelTokenDO;

/**
 * Created by mint on 16-1-21.
 */
public class CorpChannelTokenConverter {


    public static CorpChannelTokenVO corpChTokenDO2CorpChTokenVO(CorpChannelTokenDO corpChTokenDO){
        if(null==corpChTokenDO){
            return null;
        }
        CorpChannelTokenVO CorpChTokenVO = new CorpChannelTokenVO();
        CorpChTokenVO.setId(corpChTokenDO.getId());
        CorpChTokenVO.setGmtCreate(corpChTokenDO.getGmtCreate());
        CorpChTokenVO.setGmtModified(corpChTokenDO.getGmtModified());
        CorpChTokenVO.setCorpId(corpChTokenDO.getCorpId());
        CorpChTokenVO.setSuiteKey(corpChTokenDO.getSuiteKey());
        CorpChTokenVO.setCorpChannelToken(corpChTokenDO.getCorpChannelToken());
        CorpChTokenVO.setExpiredTime(corpChTokenDO.getExpiredTime());
        return CorpChTokenVO;
    }

    public static CorpChannelTokenDO corpChTokenVO2CorpChTokenDO(CorpChannelTokenVO CorpChTokenVO){
        if(null==CorpChTokenVO){
            return null;
        }
        CorpChannelTokenDO CorpChTokenDO = new CorpChannelTokenDO();
        CorpChTokenDO.setId(CorpChTokenVO.getId());
        CorpChTokenDO.setGmtCreate(CorpChTokenVO.getGmtCreate());
        CorpChTokenDO.setGmtModified(CorpChTokenVO.getGmtModified());
        CorpChTokenDO.setCorpId(CorpChTokenVO.getCorpId());
        CorpChTokenDO.setSuiteKey(CorpChTokenVO.getSuiteKey());
        CorpChTokenDO.setCorpChannelToken(CorpChTokenVO.getCorpChannelToken());
        CorpChTokenDO.setExpiredTime(CorpChTokenVO.getExpiredTime());
        return CorpChTokenDO;
    }
}
