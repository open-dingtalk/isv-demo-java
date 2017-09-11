package com.dingtalk.isv.access.biz.corp.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.corp.LoginUserVO;
import com.dingtalk.isv.access.api.model.corp.StaffVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.corp.StaffManageService;
import com.dingtalk.isv.access.biz.corp.model.helper.StaffConverter;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.CrmOapiRequestHelper;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.service.corp.CorpUserService;
import com.dingtalk.open.client.common.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 开放平台企业通讯录员工相关接口封装
 * Created by mint on 16-1-22.
 */
public class StaffManageServiceImpl implements StaffManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("STAFF_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(StaffManageServiceImpl.class);

    @Autowired
    private CorpUserService corpUserService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CrmOapiRequestHelper crmOapiRequestHelper;
    @Autowired
    private CorpOapiRequestHelper corpRequestHelper;

    @Override
    public ServiceResult<StaffVO> getStaff(String staffId, String corpId, String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("staffId", JSON.toJSONString(staffId)),
                LogFormatter.KeyValue.getNew("corpId", JSON.toJSONString(corpId)),
                LogFormatter.KeyValue.getNew("suiteKey", JSON.toJSONString(suiteKey))
        ));
        try {
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            String corpToken = corpTokenSr.getResult().getCorpToken();
            CorpUserDetail corpUserDetail = corpUserService.getCorpUser(corpToken, staffId);
            if (null == corpId) {
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "获取corpUser异常",
                        LogFormatter.KeyValue.getNew("staffId", staffId),
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                ));
            }
            StaffVO staffVO = StaffConverter.corpUser2StaffVO(corpUserDetail, corpId);
            return ServiceResult.success(staffVO);
        } catch (ServiceException e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "获取corpUser异常",
                    String.valueOf(e.getCode()),
                    e.getMessage(),
                    LogFormatter.KeyValue.getNew("staffId", JSON.toJSONString(staffId)),
                    LogFormatter.KeyValue.getNew("corpId", JSON.toJSONString(corpId)),
                    LogFormatter.KeyValue.getNew("suiteKey", JSON.toJSONString(suiteKey))
            ));
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("staffId", JSON.toJSONString(staffId)),
                    LogFormatter.KeyValue.getNew("corpId", JSON.toJSONString(corpId)),
                    LogFormatter.KeyValue.getNew("suiteKey", JSON.toJSONString(suiteKey))
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("staffId", JSON.toJSONString(staffId)),
                    LogFormatter.KeyValue.getNew("corpId", JSON.toJSONString(corpId)),
                    LogFormatter.KeyValue.getNew("suiteKey", JSON.toJSONString(suiteKey))
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<LoginUserVO> getStaffByAuthCode(String suitKey, String corpId, String code) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suitKey", suitKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("code", code)
        ));
        try {
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suitKey, corpId);
            String accessToken = tokenSr.getResult().getCorpToken();
            ServiceResult<LoginUserVO> loginVoSr = corpRequestHelper.getStaffByAuthCode(suitKey, corpId, accessToken, code);
            if (loginVoSr.isSuccess() && null != loginVoSr.getResult()) {
                return loginVoSr;
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "成宿异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suitKey", suitKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("code", code)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "成宿异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suitKey", suitKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("code", code)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
}
