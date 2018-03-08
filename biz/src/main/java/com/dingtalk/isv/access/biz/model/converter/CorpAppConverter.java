package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.CorpAppVO;
import com.dingtalk.isv.access.biz.model.CorpAppDO;

/**
 * Created by mint on 16-1-21.
 */
public class CorpAppConverter {

    public static CorpAppVO corpAppDO2CorpAppVO(CorpAppDO corpAppDO){
        if(null==corpAppDO){
            return null;
        }
        CorpAppVO corpAppVO = new CorpAppVO();
        corpAppVO.setId(corpAppDO.getId());
        corpAppVO.setGmtCreate(corpAppDO.getGmtCreate());
        corpAppVO.setGmtModified(corpAppDO.getGmtModified());
        corpAppVO.setAppId(corpAppDO.getAppId());
        corpAppVO.setAgentName(corpAppDO.getAgentName());
        corpAppVO.setAgentId(corpAppDO.getAgentId());
        corpAppVO.setLogoUrl(corpAppDO.getLogoUrl());
        corpAppVO.setCorpId(corpAppDO.getCorpId());
        return corpAppVO;
    }


    public static CorpAppDO corpAppVO2CorpAppDO(CorpAppVO corpAppVO){
        if(null==corpAppVO){
            return null;
        }
        CorpAppDO corpAppDO = new CorpAppDO();
        corpAppDO.setId(corpAppVO.getId());
        corpAppDO.setGmtCreate(corpAppVO.getGmtCreate());
        corpAppDO.setGmtModified(corpAppVO.getGmtModified());
        corpAppDO.setAppId(corpAppVO.getAppId());
        corpAppDO.setAgentId(corpAppVO.getAgentId());
        corpAppDO.setAgentName(corpAppVO.getAgentName());
        corpAppDO.setLogoUrl(corpAppVO.getLogoUrl());
        corpAppDO.setCorpId(corpAppVO.getCorpId());
        return corpAppDO;
    }



}
