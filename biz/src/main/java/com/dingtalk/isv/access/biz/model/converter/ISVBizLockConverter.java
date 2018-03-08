package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.ISVBizLockVO;
import com.dingtalk.isv.access.biz.model.ISVBizLockDO;

/**
 * Created by lifeng.zlf on 2017/11/19.
 */
public class ISVBizLockConverter {

    public static ISVBizLockVO isvBizLockDO2ISVBizLockVO(ISVBizLockDO isvBizLockDO){
        if(null==isvBizLockDO){
            return null;
        }
        ISVBizLockVO isvBizLockVO = new ISVBizLockVO();
        isvBizLockVO.setId(isvBizLockDO.getId());
        isvBizLockVO.setGmtCreate(isvBizLockDO.getGmtCreate());
        isvBizLockVO.setGmtModified(isvBizLockDO.getGmtModified());
        isvBizLockVO.setLockKey(isvBizLockDO.getLockKey());
        isvBizLockVO.setExpire(isvBizLockDO.getExpire());
        return isvBizLockVO;
    }

    public static ISVBizLockDO isvBizLockVO2ISVBizLockDO(ISVBizLockVO isvBizLockVO){
        if(null==isvBizLockVO){
            return null;
        }
        ISVBizLockDO isvBizLockDO = new ISVBizLockDO();
        isvBizLockDO.setId(isvBizLockVO.getId());
        isvBizLockDO.setGmtCreate(isvBizLockVO.getGmtCreate());
        isvBizLockDO.setGmtModified(isvBizLockVO.getGmtModified());
        isvBizLockDO.setLockKey(isvBizLockVO.getLockKey());
        isvBizLockDO.setExpire(isvBizLockVO.getExpire());
        return isvBizLockDO;
    }



}
