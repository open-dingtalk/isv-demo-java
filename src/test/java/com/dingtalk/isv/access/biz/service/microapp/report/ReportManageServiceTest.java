package com.dingtalk.isv.access.biz.service.microapp.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dingtalk.isv.access.api.model.microapp.report.ReportTemplate;
import com.dingtalk.isv.access.api.service.microapp.report.ReportManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lifeng.zlf on 2016/3/16.
 */
public class ReportManageServiceTest extends BaseTestCase {
    @Resource
    private ReportManageService reportManageService;
    @Test
    public void test_getReportStatus() {
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        ServiceResult<Integer> sr = reportManageService.getReportStatus(suiteKey, corpId);
        System.err.println(JSON.toJSON(sr));
    }

    @Test
    public void test_getReportTemplateList() {
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        ServiceResult<List<ReportTemplate>>  sr = reportManageService.getReportTemplateList(suiteKey, corpId, "0", "lifeng.zlf");
        System.err.println(JSON.toJSON(sr));
    }



}
