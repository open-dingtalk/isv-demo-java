package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.model.CorpChannelTokenDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 */
public class CorpChannelTokenDaoTest extends BaseTestCase {

    @Resource
    private CorpChannelTokenDao corpChannelTokenDao;

    @Test
    public void test_insert() {
        CorpChannelTokenDO corpChannelTokenDO = new CorpChannelTokenDO();
        corpChannelTokenDO.setCorpId("ding423423423");
        corpChannelTokenDO.setSuiteKey("suiteqcempfnjclsel6rl");
        corpChannelTokenDO.setCorpChannelToken("1234");
        corpChannelTokenDO.setExpiredTime(new Date());
        corpChannelTokenDao.saveOrUpdateCorpChannelToken(corpChannelTokenDO);
    }

    @Test
    public void test_getSuiteByKey() {
        String corpId="ding423423423";
        String suiteKey="suiteqcempfnjclsel6rl";
        CorpChannelTokenDO corpChannelTokenDO = corpChannelTokenDao.getCorpChannelToken(suiteKey,corpId);
        System.out.println(corpChannelTokenDO);
        Assert.isTrue(null!=corpChannelTokenDO);
    }


}
