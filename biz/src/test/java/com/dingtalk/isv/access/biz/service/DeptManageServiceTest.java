package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.DingDepartmentVO;
import com.dingtalk.isv.access.api.service.DeptManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lifeng.zlf on 2017/12/8.
 */
public class DeptManageServiceTest extends BaseTestCase{
    @Resource
    private DeptManageService deptManageService;

    @Test
    public void test(){
        String corpId = "ding9f50b15bccd16741";
        String ssoCorpSecret = "";
        String corpSecret = "";
        String suiteKey = "suitexdhgv7mn5ufoi9ui";
        Long deptId = 1L;
        ServiceResult<DingDepartmentVO> saveSr = deptManageService.saveDept(suiteKey,corpId,deptId);
        System.err.println(JSON.toJSONString(saveSr));
    }
}
