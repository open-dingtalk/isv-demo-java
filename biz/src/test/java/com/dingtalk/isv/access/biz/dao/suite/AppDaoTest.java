package com.dingtalk.isv.access.biz.dao.suite;

import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.AppDao;
import com.dingtalk.isv.access.biz.model.AppDO;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 */
public class AppDaoTest extends BaseTestCase {

    @Resource
    private AppDao appDao;

    @Test
    public void test_insert() {
        AppDO appDO = new AppDO();
        appDO.setSuiteKey("suiteytzpzchcpug3xpsm");
        appDO.setAppId(258L);
        appDao.addApp(appDO);
    }

    @Test
    public void test_getAppBySuiteKey() {
        List<AppDO> list = appDao.getAppBySuiteKey("suiteytzpzchcpug3xpsm");
        System.out.println(list);
        Assert.isTrue(null!=list);
    }


}
