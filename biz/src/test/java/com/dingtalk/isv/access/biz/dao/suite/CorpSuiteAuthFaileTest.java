package com.dingtalk.isv.access.biz.dao.suite;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.enums.suite.AuthFaileType;
import com.dingtalk.isv.access.api.enums.suite.SuitePushType;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dao.CorpSuiteAuthFaileDao;
import com.dingtalk.isv.access.biz.model.CorpSuiteAuthFaileDO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 */
public class CorpSuiteAuthFaileTest extends BaseTestCase {

    @Resource
    private CorpSuiteAuthFaileDao corpSuiteAuthFaileDao;

    @Test
    public void test_insert() {
        CorpSuiteAuthFaileDO corpSuiteAuthFaileDO = new CorpSuiteAuthFaileDO();
        corpSuiteAuthFaileDO.setSuiteKey("suiteytzpzchcpug3xpsm");
        corpSuiteAuthFaileDO.setFaileInfo("1234567");
        corpSuiteAuthFaileDO.setCorpId("0");
        corpSuiteAuthFaileDO.setAuthFaileType(AuthFaileType.GET_SUITE_TOKEN_FAILE);
        corpSuiteAuthFaileDO.setSuitePushType(SuitePushType.TMP_AUTH_CODE);
        corpSuiteAuthFaileDao.addOrUpdateCorpSuiteAuthFaileDO(corpSuiteAuthFaileDO);
    }



    @Test
    public void test_getCorpSuiteAuthFaileList() {
        List<CorpSuiteAuthFaileDO> list = corpSuiteAuthFaileDao.getCorpSuiteAuthFaileList(0,100);
        System.err.println(JSON.toJSON(list));
    }

}
