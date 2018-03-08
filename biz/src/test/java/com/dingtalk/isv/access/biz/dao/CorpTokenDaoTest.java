package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.model.CorpTokenDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 */
public class CorpTokenDaoTest extends BaseTestCase {

    @Resource
    private CorpTokenDao corpTokenDao;

    @Test
    public void test_insert() {
        CorpTokenDO corpTokenDO = new CorpTokenDO();
        corpTokenDO.setCorpId("ding423423423");
        corpTokenDO.setSuiteKey("suiteqcempfnjclsel6rl");
        corpTokenDO.setCorpToken("hahah");
        corpTokenDO.setExpiredTime(new Date());
        corpTokenDao.saveOrUpdateCorpToken(corpTokenDO);
    }

    @Test
    public void test_getSuiteByKey() {
        String corpId="ding4ed6d279061db5e7";
        String suiteKey="suiteytzpzchcpug3xpsm";
        CorpTokenDO corpTokenDO = corpTokenDao.getCorpToken(suiteKey,corpId);
        System.out.println(corpTokenDO);
        Assert.isTrue(null!=corpTokenDO);
    }

}
