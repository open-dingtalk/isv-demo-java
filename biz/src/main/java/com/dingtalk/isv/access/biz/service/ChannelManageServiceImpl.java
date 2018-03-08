package com.dingtalk.isv.access.biz.service;

import com.dingtalk.isv.access.api.model.CorpChannelTokenVO;
import com.dingtalk.isv.access.api.model.FollowerSimpleVO;
import com.dingtalk.isv.access.api.service.ChannelManageService;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.biz.dingutil.ChannelOapiRequestHelper;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 开放平台企业通讯录员工相关接口封装
 * Created by mint on 16-1-22.
 */
public class ChannelManageServiceImpl implements ChannelManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("FOLLOWER_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(ChannelManageServiceImpl.class);
    @Resource
    private ChannelOapiRequestHelper channelOapiRequestHelper;
    @Resource
    private CorpManageService corpManageService;
    @Override
    public ServiceResult<List<FollowerSimpleVO>> getChannelUserList(String corpId, String suiteKey, Integer offset, Integer size) {
        ServiceResult<CorpChannelTokenVO> channelTokenSr =corpManageService.getCorpChannelToken(suiteKey,corpId);
        String corpChannelToken = channelTokenSr.getResult().getCorpChannelToken();
        ServiceResult<List<FollowerSimpleVO>> userListSr = channelOapiRequestHelper.getChannelUserList(suiteKey, corpId, corpChannelToken,offset,size);
        return userListSr;
    }

    @Override
    public ServiceResult<FollowerSimpleVO> getChannelUserByOpenId(String corpId, String suiteKey, String openId) {

        ServiceResult<CorpChannelTokenVO> channelTokenSr =corpManageService.getCorpChannelToken(suiteKey,corpId);
        String corpChannelToken = channelTokenSr.getResult().getCorpChannelToken();
        ServiceResult<FollowerSimpleVO> userListSr = channelOapiRequestHelper.getChannelUserByOpenId(suiteKey, corpId, corpChannelToken,openId);
        return null;
    }
}
