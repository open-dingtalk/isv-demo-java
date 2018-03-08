package com.dingtalk.isv.access.biz.service.corp;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.FollowerSimpleVO;
import com.dingtalk.isv.access.api.service.ChannelManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by mint on 16-1-22.
 */
public class ChannelManagerServiceTest extends BaseTestCase {


    @Resource
    private ChannelManageService channelManageService;


    @Test
    public void test_getChannelUserList() {
        String corpId = "ding4ed6d279061db5e7";//1069022
        String suiteKey="suite4rkgtvvhr1neumx2";//16001
        ServiceResult<List<FollowerSimpleVO>> sr = channelManageService.getChannelUserList(corpId,suiteKey,0,100);
        System.err.println(JSON.toJSONString(sr));
    }


    @Test
    public void test_getChannelUserByOpenId() {
        String corpId = "ding4ed6d279061db5e7";//1069022
        String suiteKey="suite4rkgtvvhr1neumx2";//16001
        ServiceResult<FollowerSimpleVO> sr = channelManageService.getChannelUserByOpenId(corpId,suiteKey,"uwlTiPBZf3WgiE");
        System.err.println(JSON.toJSONString(sr));
    }


}
