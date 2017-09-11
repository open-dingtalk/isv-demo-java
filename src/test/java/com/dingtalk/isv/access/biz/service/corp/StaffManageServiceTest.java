package com.dingtalk.isv.access.biz.service.corp;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.LoginUserVO;
import com.dingtalk.isv.access.api.model.corp.StaffVO;
import com.dingtalk.isv.access.api.service.corp.StaffManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mint on 16-1-22.
 */
public class StaffManageServiceTest extends BaseTestCase {
    @Resource
    private StaffManageService staffManageService;

    @Test
    public void test_getStaff() {
        String suiteKey= "suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        String staffId = "dd_test";
        ServiceResult<StaffVO> sr = staffManageService.getStaff(staffId,corpId,suiteKey);
        System.err.println(JSON.toJSON(sr));
    }


    @Test
    public void test_getStaffByCode(){
        String suiteKey= "suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        List<String> users = Arrays.asList("dd_test");
        ServiceResult<LoginUserVO> userSr = staffManageService.getStaffByAuthCode(suiteKey, corpId, "1xxxx");
        System.out.print(JSON.toJSON(userSr));
    }
}
