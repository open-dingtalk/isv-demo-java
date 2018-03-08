package com.dingtalk.isv.access.biz.dao.suite;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.ChannelDao;
import com.dingtalk.isv.access.biz.model.ChannelDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 */
public class ChannelDaoTest extends BaseTestCase {

    @Resource
    private ChannelDao channelDao;

    @Test
    public void test_insert() {
        ChannelDO channelDO = new ChannelDO();
        channelDO.setSuiteKey("suiteytzpzchcpug3xpsm");
        channelDO.setAppId(268L);
        channelDao.addChannel(channelDO);
    }

    @Test
    public void test_getAppBySuiteKey() {
        List<ChannelDO> list = channelDao.getAppBySuiteKey("suiteytzpzchcpug3xpsm");
        System.out.println(list);
        Assert.isTrue(null!=list);
    }


}
