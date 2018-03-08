package com.dingtalk.isv.access.biz.dao.suite;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.CorpAppDao;
import com.dingtalk.isv.access.biz.model.CorpAppDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 */
public class CorpAppDaoTest extends BaseTestCase {

    @Resource
    private CorpAppDao corpAppDao;

    @Test
    public void test_insert() {
        CorpAppDO corpAppDO = new CorpAppDO();
        corpAppDO.setCorpId("ding423423423");
        corpAppDO.setAgentId(123322L);
        corpAppDO.setAppId(158L);
        corpAppDO.setLogoUrl("http://baidu.com");
        corpAppDO.setAgentName("云考勤");
        corpAppDao.saveOrUpdateCorpApp(corpAppDO);
    }

    @Test
    public void test_getCorpApp() {
        CorpAppDO corpAppDO = corpAppDao.getCorpApp("ding423423423",158L);
        System.out.println(corpAppDO);
        Assert.isTrue(null!=corpAppDO);
    }

}
