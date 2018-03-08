package com.dingtalk.isv.access.biz.dao.suite;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.CorpChannelAppDao;
import com.dingtalk.isv.access.biz.model.CorpChannelAppDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 */
public class CorpChannelAppDaoTest extends BaseTestCase {

    @Resource
    private CorpChannelAppDao corpChannelAppDao;

    @Test
    public void test_insert() {
        CorpChannelAppDO corpChannelAppDO = new CorpChannelAppDO();
        corpChannelAppDO.setCorpId("ding423423423");
        corpChannelAppDO.setAgentId(123322L);
        corpChannelAppDO.setAppId(158L);
        corpChannelAppDO.setLogoUrl("http://baidu.com");
        corpChannelAppDO.setAgentName("云考勤");
        corpChannelAppDao.saveOrUpdateCorpChannelApp(corpChannelAppDO);
    }

    @Test
    public void test_getCorpChannelApp() {
        CorpChannelAppDO corpChannelAppDO = corpChannelAppDao.getCorpChannelApp("ding423423423",158L);
        System.out.println(corpChannelAppDO);
        Assert.isTrue(null!=corpChannelAppDO);
    }

}
