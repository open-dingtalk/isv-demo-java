package com.dingtalk.isv.access.biz.service.org;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.corp.CorpChannelTokenVO;
import com.dingtalk.isv.access.api.model.corp.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.org.OrgChannelTokenVO;
import com.dingtalk.isv.access.api.model.org.OrgJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgTokenVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.org.OrgManangerService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by mint on 16-1-22.
 */
public class OrgManageServiceTest extends BaseTestCase {


    @Resource
    private OrgManangerService orgManangerService;
    @Test
    public void test_getOrgToken() {
        String corpid = "ding4ed6d279061db5e7";
        String corpSecret = "B_x7uXxp8AcU_4ibCmoUXLBCa_g3Z8kK6Z_cYuhI9La6oxgCrav-FonKoHPzi-kb";
        ServiceResult<OrgTokenVO>  sr = orgManangerService.getOrgToken(corpid,corpSecret);
        System.err.println(JSON.toJSONString(sr));
    }


    @Test
    public void test_getOrgJSAPITicket() {
        String corpid = "ding4ed6d279061db5e7";
        String orgToken = "8623ad74372c337eb214cccd9a0dd9b8";
        ServiceResult<OrgJSAPITicketVO>  sr = orgManangerService.getOrgJSAPITicket(corpid,orgToken);
        System.err.println(JSON.toJSONString(sr));
    }


    @Test
    public void test_getOrgChannelToken() {
        String corpid = "ding4ed6d279061db5e7";
        String corpChannelSecret = "qC5wh7S-1b1az2_6BOh2Y1n4kiU_Ene5xJxFSFo5jyXksqgIZeQsWjCdlSQW7iRv";
        ServiceResult<OrgChannelTokenVO>  sr = orgManangerService.getOrgChannelToken(corpid,corpChannelSecret);
        System.err.println(JSON.toJSONString(sr));
    }

}
