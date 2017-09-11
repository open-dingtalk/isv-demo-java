package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dingutil.CrmOapiRequestHelper;
import com.dingtalk.isv.common.model.ResultWrapper;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by lifeng.zlf on 2016/3/24.
 */
public class CrmOapiRequestHelperTest extends BaseTestCase {
    @Autowired
    private CrmOapiRequestHelper crmOapiRequestHelper;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Test
    public void test_registerCorpSuiteCallback() {
        String suiteKey ="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        ServiceResult<SuiteVO> sr = suiteManageService.getSuiteByKey(suiteKey);
        SuiteVO suiteVO = sr.getResult();
        ServiceResult<CorpTokenVO> ssr = corpManageService.getCorpToken(suiteKey, corpId);
        CorpTokenVO corpTokenVO = ssr.getResult();
        crmOapiRequestHelper.saveCorpCallback(suiteVO.getSuiteKey(),suiteVO.getToken(),suiteVO.getEncodingAesKey(),"http://100.69.166.198:7001/suite/corp_callback/suiteytzpzchcpug3xpsm",corpId,corpTokenVO.getCorpToken(), Arrays.asList("user_add_org"));
    }



    @Test
    public void test_getCrmCustomerIdList() {
        String suiteKey ="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        ServiceResult<CorpTokenVO> ssr = corpManageService.getCorpToken(suiteKey, corpId);
        CorpTokenVO corpTokenVO = ssr.getResult();
        ServiceResult<ResultWrapper<String>> sr = crmOapiRequestHelper.getCrmCustomerIdList(suiteKey, corpId, corpTokenVO.getCorpToken(), 0, 100);
        System.err.println(JSON.toJSONString(sr));
    }



}
