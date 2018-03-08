package com.dingtalk.isv.access.biz.service.corp;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.CorpChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.CorpChannelTokenVO;
import com.dingtalk.isv.access.api.model.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by mint on 16-1-22.
 */
public class CorpManageServiceTest extends BaseTestCase {


    @Resource
    private CorpManageService corpManageService;
    @Test
    public void test_getCorpToken() {
        String corpId="ding4ed6d279061db5e7";
        String suiteKey="suiteytzpzchcpug3xpsm";
        ServiceResult<CorpTokenVO> sr = corpManageService.getCorpToken(suiteKey,corpId);
        System.out.println(JSON.toJSON(sr));

    }

    @Test
    public void test_getCorpJSAPITicket() {
        Long startTime =System.currentTimeMillis();
        String corpId="ding4ed6d279061db5e7";
        String suiteKey="suiteytzpzchcpug3xpsm";
        ServiceResult<CorpJSAPITicketVO> sr = corpManageService.getCorpJSAPITicket(suiteKey, corpId);
        System.err.println(JSON.toJSONString(sr));
        System.err.println("rt:"+(System.currentTimeMillis()-startTime));
    }


    @Test
    public void test_getCorpChannelToken() {
        String corpId = "ding4ed6d279061db5e7";//1069022
        String suiteKey="suite4rkgtvvhr1neumx2";//16001
        ServiceResult<CorpChannelTokenVO> sr = corpManageService.getCorpChannelToken(suiteKey,corpId);
        System.err.println(JSON.toJSONString(sr));
    }



    @Test
    public void test_getCorpChannelJSAPITicket() {
        String corpId = "ding4ed6d279061db5e7";//1069022
        String suiteKey="suite4rkgtvvhr1neumx2";//16001
        ServiceResult<CorpChannelJSAPITicketVO> sr = corpManageService.getCorpChannelJSAPITicket(suiteKey,corpId);
        System.err.println(JSON.toJSONString(sr));
    }



}
