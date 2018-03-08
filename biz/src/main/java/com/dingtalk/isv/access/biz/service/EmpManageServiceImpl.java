package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.model.LoginUserVO;
import com.dingtalk.isv.access.api.model.EmpVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.EmpManageService;
import com.dingtalk.isv.access.biz.model.converter.StaffConverter;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.service.corp.CorpUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 开放平台企业通讯录员工相关接口封装
 */
public class EmpManageServiceImpl implements EmpManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("STAFF_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(EmpManageServiceImpl.class);
    @Autowired
    private CorpUserService corpUserService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpOapiRequestHelper corpRequestHelper;
    @Override
    public ServiceResult<EmpVO> getEmpByUserId(String staffId, String corpId, String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("staffId", JSON.toJSONString(staffId)),
                LogFormatter.KeyValue.getNew("corpId", JSON.toJSONString(corpId)),
                LogFormatter.KeyValue.getNew("suiteKey", JSON.toJSONString(suiteKey))
        ));
        try {
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            String corpToken = corpTokenSr.getResult().getCorpToken();
            //使用SDK查询单个用户
            CorpUserDetail corpUserDetail = corpUserService.getCorpUser(corpToken, staffId);
            if (null == corpUserDetail) {
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "获取corpUser异常",
                        LogFormatter.KeyValue.getNew("staffId", staffId),
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                ));
            }
            //将SDK中的VO对象转换为系统内的VO。方便系统处理
            EmpVO staffVO = StaffConverter.corpUser2StaffVO(corpUserDetail, corpId);
            //如果需要本地持久化存储，可以存到本地
            return ServiceResult.success(staffVO);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("staffId", JSON.toJSONString(staffId)),
                    LogFormatter.KeyValue.getNew("corpId", JSON.toJSONString(corpId)),
                    LogFormatter.KeyValue.getNew("suiteKey", JSON.toJSONString(suiteKey))
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<LoginUserVO> getEmpByAuthCode(String suitKey, String corpId, String code) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suitKey", suitKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("code", code)
        ));
        try {
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suitKey, corpId);
            String accessToken = tokenSr.getResult().getCorpToken();
            //使用临时授权码换取用户的登录身份
            ServiceResult<LoginUserVO> loginVoSr = corpRequestHelper.getEmpByAuthCode(suitKey, corpId, accessToken, code);
            if (!loginVoSr.isSuccess() && null == loginVoSr.getResult()) {
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        "免登失败",
                        loginVoSr.getCode(),
                        loginVoSr.getMessage(),
                        LogFormatter.KeyValue.getNew("suitKey", suitKey),
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("code", code)
                ));
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
            }
            return loginVoSr;
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suitKey", suitKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("code", code)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
}
