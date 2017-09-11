package com.dingtalk.isv.access.biz.microapp.report;

import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.microapp.report.ReportTemplate;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.microapp.report.ReportManageService;
import com.dingtalk.isv.access.biz.dingutil.ReportOapiRequestHelper;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lifeng.zlf on 2016/3/11.
 */
public class ReportManageServiceImpl implements ReportManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("REPORT_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(ReportManageServiceImpl.class);
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private ReportOapiRequestHelper reportOapiRequestHelper;


    @Override
    public ServiceResult<Integer> getReportStatus(String suiteKey, String corpId){
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            String corpToken = corpTokenSr.getResult().getCorpToken();
            ServiceResult<Integer> sr = reportOapiRequestHelper.getReportStatus(suiteKey, corpId, corpToken);
            if(sr.isSuccess()){
                return sr;
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<List<ReportTemplate>> getReportTemplateList(String suiteKey, String corpId, String reportType,String staffId) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            String corpToken = corpTokenSr.getResult().getCorpToken();
            ServiceResult<List<ReportTemplate>>  sr = reportOapiRequestHelper.getReportTemplateList(suiteKey, corpId, corpToken, reportType,staffId);
            if(sr.isSuccess()){
                return sr;
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
