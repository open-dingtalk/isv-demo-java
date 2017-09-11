package com.dingtalk.isv.access.api.service.microapp.report;

import com.dingtalk.isv.access.api.model.microapp.report.ReportTemplate;
import com.dingtalk.isv.common.model.ServiceResult;

import java.util.List;

/**
 * Created by lifeng.zlf on 2016/5/25.
 */
public interface ReportManageService {


    /**
     * 获取日志状态,日志是否开通,禁用,等等
     * @param suiteKey
     * @param corpId
     * @return
     */
    public ServiceResult<Integer> getReportStatus(String suiteKey, String corpId);


    /**
     * 查询日志模板类型
     * @param suiteKey
     * @param corpId
     * @param reportType 日志模板状态
     * @param staffId
     * @return
     */
    public ServiceResult<List<ReportTemplate>> getReportTemplateList(String suiteKey, String corpId, String reportType,String staffId);


}
