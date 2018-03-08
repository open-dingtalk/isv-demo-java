package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.CorpChannelAppVO;
import com.dingtalk.isv.access.biz.model.CorpChannelAppDO;

/**
 * Created by mint on 16-1-21.
 */
public class CorpChannelAppConverter {

    public static CorpChannelAppVO corpChannelAppDO2CorpChannelAppVO(CorpChannelAppDO corpChannelAppDO){
        if(null==corpChannelAppDO){
            return null;
        }
        CorpChannelAppVO corpChannelAppVO = new CorpChannelAppVO();
        corpChannelAppVO.setId(corpChannelAppDO.getId());
        corpChannelAppVO.setGmtCreate(corpChannelAppDO.getGmtCreate());
        corpChannelAppVO.setGmtModified(corpChannelAppDO.getGmtModified());
        corpChannelAppVO.setAppId(corpChannelAppDO.getAppId());
        corpChannelAppVO.setAgentName(corpChannelAppDO.getAgentName());
        corpChannelAppVO.setAgentId(corpChannelAppDO.getAgentId());
        corpChannelAppVO.setLogoUrl(corpChannelAppDO.getLogoUrl());
        corpChannelAppVO.setCorpId(corpChannelAppDO.getCorpId());
        return corpChannelAppVO;
    }


    public static CorpChannelAppDO corpChannelAppVO2CorpChannelAppDO(CorpChannelAppVO corpChannelAppVO){
        if(null==corpChannelAppVO){
            return null;
        }
        CorpChannelAppDO corpChannelAppDO = new CorpChannelAppDO();
        corpChannelAppDO.setId(corpChannelAppVO.getId());
        corpChannelAppDO.setGmtCreate(corpChannelAppVO.getGmtCreate());
        corpChannelAppDO.setGmtModified(corpChannelAppVO.getGmtModified());
        corpChannelAppDO.setAppId(corpChannelAppVO.getAppId());
        corpChannelAppDO.setAgentId(corpChannelAppVO.getAgentId());
        corpChannelAppDO.setAgentName(corpChannelAppVO.getAgentName());
        corpChannelAppDO.setLogoUrl(corpChannelAppVO.getLogoUrl());
        corpChannelAppDO.setCorpId(corpChannelAppVO.getCorpId());
        return corpChannelAppDO;
    }



}
