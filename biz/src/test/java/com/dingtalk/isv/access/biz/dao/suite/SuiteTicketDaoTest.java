package com.dingtalk.isv.access.biz.dao.suite;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.SuiteTicketDao;
import com.dingtalk.isv.access.biz.model.SuiteTicketDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 */
public class SuiteTicketDaoTest extends BaseTestCase {

    @Resource
    private SuiteTicketDao suiteTicketDao;

    @Test
    public void test_saveOrUpdateSuiteTicket() {
        String suiteKey = "suiteytzpzchcpug3xpsm";
        SuiteTicketDO suiteTicketDO = new SuiteTicketDO();
        suiteTicketDO.setGmtCreate(new Date());
        suiteTicketDO.setGmtModified(new Date());
        suiteTicketDO.setSuiteKey(suiteKey);
        suiteTicketDO.setSuiteTicket("wxvOv4tIy1LWjBd8Yy3RfCXkfdIUQ1VxfFUG5TaaSY3KtWk5nDiQ58YrI3RDExHxkQKF9zLBlWkCrxKWcYAnia");
        suiteTicketDao.saveOrUpdateSuiteTicket(suiteTicketDO);

        suiteTicketDO.setSuiteTicket("654321");
        suiteTicketDao.saveOrUpdateSuiteTicket(suiteTicketDO);
    }

    @Test
    public void test_getSuiteTicketByKey() {
        SuiteTicketDO suiteTicketDO = suiteTicketDao.getSuiteTicketByKey("suiteytzpzchcpug3xpsm");
        System.out.println(suiteTicketDO);
        Assert.isTrue(null!=suiteTicketDO);
    }
}
