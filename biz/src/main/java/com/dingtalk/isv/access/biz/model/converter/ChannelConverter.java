package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.ChannelVO;
import com.dingtalk.isv.access.biz.model.ChannelDO;

/**
 * Created by lifeng.zlf on 2016/1/15.
 */

public class ChannelConverter {

   public static ChannelVO channelVO2ChannelVO(ChannelDO channelDO){
       if(null==channelDO){
            return null;
       }
       ChannelVO channelVO = new ChannelVO();
       channelVO.setId(channelDO.getId());
       channelVO.setGmtCreate(channelDO.getGmtCreate());
       channelVO.setGmtModified(channelDO.getGmtModified());
       channelVO.setAppId(channelDO.getAppId());
       return channelVO;
   }

    public static ChannelDO channelDO2ChannelDO(ChannelVO channelVO){
        if(null==channelVO){
            return null;
        }
        ChannelDO channelDO = new ChannelDO();
        channelDO.setId(channelVO.getId());
        channelDO.setGmtCreate(channelVO.getGmtCreate());
        channelDO.setGmtModified(channelVO.getGmtModified());
        channelDO.setAppId(channelVO.getAppId());
        return channelDO;
    }
}
