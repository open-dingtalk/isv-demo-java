package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.corp.StaffVO;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lifeng.zlf on 2016/3/24.
 */
public class CorpOapiRequestHelperTest extends BaseTestCase {
    @Autowired
    private CorpOapiRequestHelper corpRequestHelper;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Test
    public void test_getAdmin() {
        String suiteKey ="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        ServiceResult<SuiteVO> sr = suiteManageService.getSuiteByKey(suiteKey);
        SuiteVO suiteVO = sr.getResult();
        ServiceResult<CorpTokenVO> ssr = corpManageService.getCorpToken(suiteKey, corpId);
        CorpTokenVO corpTokenVO = ssr.getResult();
        ServiceResult<List<StaffVO>> adminSr = corpRequestHelper.getAdmin(suiteKey, corpId, corpTokenVO.getCorpToken());
        System.err.println(JSON.toJSONString(adminSr));
    }
}
