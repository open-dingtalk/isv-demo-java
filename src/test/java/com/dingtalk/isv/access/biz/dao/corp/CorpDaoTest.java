package com.dingtalk.isv.access.biz.dao.corp;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpAppVO;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.corp.dao.CorpDao;
import com.dingtalk.isv.access.biz.corp.model.CorpDO;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.Assert;

import javax.annotation.Resource;
/**
 */
public class CorpDaoTest extends BaseTestCase {

    @Resource
    private CorpDao corpDao;

    @Test
    public void test_insert() {
        CorpDO corpDO = new CorpDO();
        corpDO.setCorpId("ding123456777");
        corpDO.setCorpLogoUrl("http://baidu.com");
        corpDO.setIndustry("教育11");
        corpDO.setInviteCode("3000");
        corpDO.setCorpName("皮包公司");
        corpDao.saveOrUpdateCorp(corpDO);

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
