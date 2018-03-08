package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.CorpVO;
import com.dingtalk.isv.access.biz.model.CorpDO;

/**
 * Created by mint on 16-1-21.
 */
public class CorpConverter {


    public static CorpVO CorpDO2CorpVO(CorpDO corpDO){
        if(null==corpDO){
            return null;
        }
        CorpVO corpVO = new CorpVO();
        corpVO.setId(corpDO.getId());
        corpVO.setGmtCreate(corpDO.getGmtCreate());
        corpVO.setGmtModified(corpDO.getGmtModified());
        corpVO.setInviteCode(corpDO.getInviteCode());
        corpVO.setInviteUrl(corpDO.getInviteUrl());
        corpVO.setIndustry(corpDO.getIndustry());
        corpVO.setCorpName(corpDO.getCorpName());
        corpVO.setCorpId(corpDO.getCorpId());
        corpVO.setCorpLogoUrl(corpDO.getCorpLogoUrl());
        return corpVO;
    }

    public static CorpDO CorpVO2CorpDO(CorpVO corpVO){
        if(null==corpVO){
            return null;
        }
        CorpDO corpDO = new CorpDO();
        corpDO.setId(corpVO.getId());
        corpDO.setGmtCreate(corpVO.getGmtCreate());
        corpDO.setGmtModified(corpVO.getGmtModified());
        corpDO.setInviteCode(corpVO.getInviteCode());
        corpDO.setInviteUrl(corpVO.getInviteUrl());
        corpDO.setIndustry(corpVO.getIndustry());
        corpDO.setCorpName(corpVO.getCorpName());
        corpDO.setCorpId(corpVO.getCorpId());
        corpDO.setCorpLogoUrl(corpVO.getCorpLogoUrl());
        return corpDO;
    }
}
