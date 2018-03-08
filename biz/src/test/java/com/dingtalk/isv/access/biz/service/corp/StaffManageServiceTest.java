package com.dingtalk.isv.access.biz.service.corp;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.LoginUserVO;
import com.dingtalk.isv.access.api.model.EmpVO;
import com.dingtalk.isv.access.api.service.EmpManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mint on 16-1-22.
 */
public class StaffManageServiceTest extends BaseTestCase {
    @Resource
    private EmpManageService empManageService;

    @Test
    public void test_getStaff() {
        String suiteKey= "suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        String staffId = "dd_test";
        ServiceResult<EmpVO> sr = empManageService.getEmpByUserId(staffId,corpId,suiteKey);
        System.err.println(JSON.toJSON(sr));
    }


    @Test
    public void test_getStaffByCode(){
        String suiteKey= "suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        List<String> users = Arrays.asList("dd_test");
        ServiceResult<LoginUserVO> userSr = empManageService.getEmpByAuthCode(suiteKey, corpId, "1xxxx");
        System.out.print(JSON.toJSON(userSr));
    }
}
