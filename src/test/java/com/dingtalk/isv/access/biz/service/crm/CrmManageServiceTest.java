package com.dingtalk.isv.access.biz.service.crm;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.crm.CrmContactVO;
import com.dingtalk.isv.access.api.model.crm.CrmCustomerVO;
import com.dingtalk.isv.access.api.service.crm.CrmManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by mint on 16-1-22.
 */
public class CrmManageServiceTest extends BaseTestCase {
    @Resource
    private CrmManageService crmManageService;
    @Test
    public void test_getCrmCustomer() {
        Long start = System.currentTimeMillis();
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        String customId ="MjA1MDAyXzY5MDQ";
        //323135303031，323135303032，323135303033，4D546B334D444178587A4D314D6A6B
        ServiceResult<CrmCustomerVO> sr = crmManageService.getCrmCustomer(suiteKey, corpId, customId);
        System.err.println("cost:"+(System.currentTimeMillis()-start));
        System.err.println(JSON.toJSONString(sr));
    }

    @Test
    public void test_getCrmContactList() {
        Long start = System.currentTimeMillis();
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        String customId ="MjA1MDAyXzY5MDQ";
        //323135303031，323135303032，323135303033，4D546B334D444178587A4D314D6A6B
        ServiceResult<List<CrmContactVO>> sr = crmManageService.getCrmContactList(suiteKey, corpId, customId,0,10);
        System.err.println("cost:"+(System.currentTimeMillis()-start));
        System.err.println(JSON.toJSONString(sr));
    }

    @Test
    public void test_bindCustom() {
        Long start = System.currentTimeMillis();
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        String customId ="MjA1MDAyXzY5MDQ";
        String staffId = "";
        //323135303031，323135303032，323135303033，4D546B334D444178587A4D314D6A6B
        ServiceResult<Void> sr = crmManageService.bindCustom(suiteKey, corpId, customId, "18768143018");
        System.err.println("cost:"+(System.currentTimeMillis()-start));
        System.err.println(JSON.toJSONString(sr));
    }


    @Test
    public void test_updateFollowTime() {
        Long start = System.currentTimeMillis();
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        String customId ="MjA1MDAyXzY5MDQ";
        ServiceResult<Void> sr = crmManageService.updateFollowTime(suiteKey, corpId, customId, System.currentTimeMillis());
        System.err.println("cost:"+(System.currentTimeMillis()-start));
        System.err.println(JSON.toJSONString(sr));
    }



    @Test
    public void test_getCustomerBaseForm() {
        Long start = System.currentTimeMillis();
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        ServiceResult<Void> sr = crmManageService.getCustomerBaseForm(suiteKey, corpId);
        System.err.println("cost:"+(System.currentTimeMillis()-start));
        System.err.println(JSON.toJSONString(sr));
    }
}
