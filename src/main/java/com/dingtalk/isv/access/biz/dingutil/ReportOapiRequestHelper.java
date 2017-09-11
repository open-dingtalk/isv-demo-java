package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.constant.CommonUtils;
import com.dingtalk.isv.access.api.model.microapp.report.ReportTemplate;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.isv.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 日志类微应用开放平台接口请求
 * Created by lifeng.zlf on 2016/1/21.
 */
public class ReportOapiRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(ReportOapiRequestHelper.class);
    private static final Logger bizLogger = LoggerFactory.getLogger("HTTP_INVOKE_LOGGER");
    private String oapiDomain;
    private HttpRequestHelper httpRequestHelper;

    public String getOapiDomain() {
        return oapiDomain;
    }

    public void setOapiDomain(String oapiDomain) {
        this.oapiDomain = oapiDomain;
    }

    public HttpRequestHelper getHttpRequestHelper() {
        return httpRequestHelper;
    }

    public void setHttpRequestHelper(HttpRequestHelper httpRequestHelper) {
        this.httpRequestHelper = httpRequestHelper;
    }

    /**
     *
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @return
     */
    public ServiceResult<Integer> getReportStatus(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/report/get_report_status?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                Integer reportStatus = jsonObject.getInteger("status");
                return ServiceResult.success(reportStatus);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     *
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @param reportType
     * @param staffId
     * @return
     */
    public ServiceResult<List<ReportTemplate>> getReportTemplateList(String suiteKey, String corpId,String accessToken,String reportType,String staffId){
        try {
            List<ReportTemplate> reportTemplateList = new ArrayList<ReportTemplate>();
            String url = getOapiDomain() + "/report/get_report_template?access_token=" + accessToken+"&report_type="+reportType+"&userId="+staffId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                JSONArray reportTemplateArr = jsonObject.getJSONArray("templateList");
                for(int i=0;i<reportTemplateArr.size();i++){
                    JSONObject template =  reportTemplateArr.getJSONObject(i);
                    ReportTemplate reportTemplate = new ReportTemplate();
                    reportTemplate.setTemplateId(template.getString("templateId"));
                    reportTemplate.setTemplateName(template.getString("name"));
                    reportTemplate.setIcon(template.getString("icon"));
                    reportTemplate.setUrl(template.getString("url"));
                    reportTemplateList.add(reportTemplate);
                }
                return ServiceResult.success(reportTemplateList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }



}
