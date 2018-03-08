package com.dingtalk.isv.access.api.service;
import com.dingtalk.isv.access.api.model.FollowerSimpleVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

import java.util.List;

/**
 * 员工管理
 * Created by 浩倡 on 16-1-17.
 */
public interface ChannelManageService {

    /**
     * 获取关注者列表
     * @param corpId
     * @param suiteKey
     * @return
     */
    public ServiceResult<List<FollowerSimpleVO>> getChannelUserList(String corpId, String suiteKey, Integer offset, Integer size);

    public ServiceResult<FollowerSimpleVO> getChannelUserByOpenId(String corpId, String suiteKey,String openId);



}
