package com.dingtalk.isv.access.biz.service.suite;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpAppVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteCallBackVO;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by mint on 16-1-22.
 */
public class CorpSuiteAuthServiceTest extends BaseTestCase {
    @Resource
    private CorpSuiteAuthService corpSuiteAuthService;
    @Resource
    private SuiteManageService suiteManageService;
    @Test
    public void test_saveOrUpdateCorpSuiteAuth() {
        CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
        corpSuiteAuthVO.setSuiteKey("suiteytzpzchcpug3xpsm");
        corpSuiteAuthVO.setPermanentCode("v8uFm67epk-ZttRL5THnOIRVx8m8reQ7muT6ypd8XkKHeGHLycwd2I_CGbXpTxVX");
        corpSuiteAuthVO.setCorpId("ding4ed6d279061db5e7");
        ServiceResult<Void> sr = corpSuiteAuthService.saveOrUpdateCorpSuiteAuth(corpSuiteAuthVO);
        System.out.println(JSON.toJSON(sr));
        Assert.isTrue(null!=sr.getResult());
    }


    @Test
    public void test_saveOrUpdateCorpApp() {
        CorpAppVO corpAppVO = new CorpAppVO();
        corpAppVO.setAgentId(1L);
        corpAppVO.setAppId(158L);
        corpAppVO.setCorpId("ding4ed6d279061db5e7");
        corpAppVO.setAgentName("考了个擒");
        ServiceResult<Void> sr = corpSuiteAuthService.saveOrUpdateCorpApp(corpAppVO);
        System.out.println(JSON.toJSON(sr));
    }

    @Test
    public void test_saveCorpCallback() {
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpid= "ding4ed6d279061db5e7";
        ServiceResult<Void> sr = corpSuiteAuthService.saveCorpCallback(suiteKey,corpid,"http://100.69.166.198:7001/suite/corp_callback/suiteytzpzchcpug3xpsm", Arrays.asList("user_add_org"));
        System.out.println(JSON.toJSON(sr));
    }


    @Test
    public void test_getCorpCallback() {
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpid= "ding4ed6d279061db5e7";
        ServiceResult<CorpSuiteCallBackVO> sr = corpSuiteAuthService.getCorpCallback(suiteKey,corpid);
        System.out.println(JSON.toJSON(sr));
    }



    @Test
    public void test_getCorpInfo() {
        String corpId = "ding4ed6d279061db5e7";//1069022
        String suiteKey="suite4rkgtvvhr1neumx2";//16001
        String suiteToken = suiteManageService.getSuiteToken(suiteKey).getResult().getSuiteToken();
        ServiceResult<Void> sr = corpSuiteAuthService.getCorpInfo(suiteToken,suiteKey,corpId,"");
        System.err.println(JSON.toJSON(sr));
    }












}
