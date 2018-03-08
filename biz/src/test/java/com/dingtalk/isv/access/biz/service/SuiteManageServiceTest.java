package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 */
public class SuiteManageServiceTest extends BaseTestCase {
    @Resource
    private SuiteManageService suiteManageService;

    @Test
    public void test(){
        String suiteKey = "suitesvkbhtbt6oodezgt";
        ServiceResult<SuiteTokenVO> sr = suiteManageService.getSuiteToken(suiteKey);
        System.err.println(JSON.toJSONString(sr));
    }
}
