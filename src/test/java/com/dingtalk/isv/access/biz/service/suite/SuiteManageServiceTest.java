package com.dingtalk.isv.access.biz.service.suite;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 */
public class SuiteManageServiceTest extends BaseTestCase {
    @Resource
    private SuiteManageService suiteManageService;
    @Test
    public void test_getAllSuiteByKey() {
        ServiceResult<List<SuiteVO>> sr = suiteManageService.getAllSuite();
        System.out.println(JSON.toJSON(sr));
        Assert.isTrue(null!=sr.getResult());
    }

    @Test
    public void test_saveOrUpdateSuiteToken() {
        String suiteKey = "suiteqcempfnjclsel6rl";
        ServiceResult<Void> sr = suiteManageService.saveOrUpdateSuiteToken(suiteKey);
        Assert.isTrue(null==sr.getResult());
    }


}
