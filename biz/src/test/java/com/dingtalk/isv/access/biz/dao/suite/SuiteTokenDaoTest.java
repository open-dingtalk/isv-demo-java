package com.dingtalk.isv.access.biz.dao.suite;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.SuiteTokenDao;
import com.dingtalk.isv.access.biz.model.SuiteTokenDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 */
public class SuiteTokenDaoTest extends BaseTestCase {

    @Resource
    private SuiteTokenDao suiteTokenDao;

    @Test
    public void test_saveOrUpdateSuiteToken() {
        String suiteKey = "suiteytzpzchcpug3xpsm";
        SuiteTokenDO suiteTokenDO = new SuiteTokenDO();
        suiteTokenDO.setGmtCreate(new Date());
        suiteTokenDO.setGmtModified(new Date());
        suiteTokenDO.setSuiteKey(suiteKey);
        suiteTokenDO.setSuiteToken("123");
        suiteTokenDO.setExpiredTime(new Date());
        suiteTokenDao.saveOrUpdateSuiteToken(suiteTokenDO);

        suiteTokenDO.setSuiteToken("a3325c48d24b385e86c54116ede6d133");
        suiteTokenDao.saveOrUpdateSuiteToken(suiteTokenDO);
    }

    @Test
    public void test_getSuiteTicketByKey() {
        SuiteTokenDO suiteTokenDO = suiteTokenDao.getSuiteTokenByKey("suiteytzpzchcpug3xpsm");
        System.out.println(suiteTokenDO);
        Assert.isTrue(null!=suiteTokenDO);
    }
}
