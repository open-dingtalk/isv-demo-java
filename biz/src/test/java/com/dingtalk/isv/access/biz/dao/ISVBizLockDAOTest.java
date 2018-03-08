package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 */
public class ISVBizLockDAOTest extends BaseTestCase {

    @Resource
    private ISVBizLockDAO isvBizLockDAO;

    @Test
    public void test_saveISVBizLock() {
        isvBizLockDAO.saveISVBizLock("555",new Date());
    }

    @Test
    public void test_getSuiteByKey() {
        /**

        CorpDO corpDO = corpDao.getCorpByCorpId("ding4ed6d279061db5e7");
        System.out.println(corpDO);
        Assert.isTrue(null!=corpDO);
        **/
    }

}
