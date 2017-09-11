package com.dingtalk.isv.access.biz.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.crm.CrmContactVO;
import com.dingtalk.isv.access.api.model.crm.CrmCustomerVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.crm.CrmManageService;
import com.dingtalk.isv.access.biz.dingutil.CrmOapiRequestHelper;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ResultWrapper;
import com.dingtalk.isv.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lifeng.zlf on 2016/3/1.
 */
public class CrmManageServiceImpl implements CrmManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("CRM_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(CrmManageServiceImpl.class);
    @Resource
    private CorpManageService corpManageService;
    @Autowired
    private CrmOapiRequestHelper crmOapiRequestHelper;
    @Override
    public ServiceResult<CrmCustomerVO> getCrmCustomer(String suiteKey, String corpId, String customerId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("customerId", customerId)
        ));
        try{
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<CrmCustomerVO>  sr = crmOapiRequestHelper.getCrmCustomer(suiteKey, corpId, corpTokenVO.getCorpToken(), customerId);
            if(!sr.isSuccess()){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "查询客户信息失败",
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("customerId", customerId)
                ));
                return ServiceResult.failure(ServiceResultCode.CUSTOM_FIND_ERROR.getErrCode(),ServiceResultCode.CUSTOM_FIND_ERROR.getErrMsg());
            }
            return sr;
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<Void> removeCrmCustomer(String suiteKey, String corpId, String customerId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("customerId", customerId)
        ));
        try{
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<Void>  sr = crmOapiRequestHelper.removeCrmCustomer(suiteKey, corpId, corpTokenVO.getCorpToken(), customerId);
            if(!sr.isSuccess()){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "删除客户信息失败",
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("customerId", customerId)
                ));
                return ServiceResult.failure(ServiceResultCode.CUSTOM_REMOVE_ERROR.getErrCode(), ServiceResultCode.CUSTOM_REMOVE_ERROR.getErrMsg());
            }
            return sr;
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<List<CrmContactVO>> getCrmContactList(String suiteKey,String corpId, String customerId,Integer offset,Integer limit) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("customerId", customerId),
                LogFormatter.KeyValue.getNew("offset", offset),
                LogFormatter.KeyValue.getNew("limit", limit)
        ));
        try{
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<List<CrmContactVO>>  sr = crmOapiRequestHelper.getCrmContactList(suiteKey, corpId, corpTokenVO.getCorpToken(), customerId);
            if(!sr.isSuccess()){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "删除客户联系人信息失败",
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("customerId", customerId)
                ));
                return ServiceResult.failure(ServiceResultCode.CONTACT_FIND_ERROR.getErrCode(), ServiceResultCode.CONTACT_FIND_ERROR.getErrMsg());
            }
            return sr;
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<CrmContactVO> getCrmContact(String suiteKey, String corpId, String customerId, String contractId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("customerId", customerId),
                LogFormatter.KeyValue.getNew("contractId", contractId)
        ));
        try{
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<CrmContactVO>  sr = crmOapiRequestHelper.getCrmContact(suiteKey, corpId, corpTokenVO.getCorpToken(), customerId,contractId);
            if(!sr.isSuccess()){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "查询客户联系人信息失败",
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("customerId", customerId),
                        LogFormatter.KeyValue.getNew("contractId", contractId)
                ));
                return ServiceResult.failure(ServiceResultCode.CONTACT_FIND_ERROR.getErrCode(), ServiceResultCode.CONTACT_FIND_ERROR.getErrMsg());
            }
            return sr;
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("contractId", contractId)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("contractId", contractId)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }





    @Override
    public ServiceResult<Void> bindCustom(String suiteKey, String corpId, String customerId, String statffId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("customerId", customerId),
                LogFormatter.KeyValue.getNew("statffId", statffId)
        ));
        try{
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<Void>  sr = crmOapiRequestHelper.bindCustom(suiteKey, corpId, corpTokenVO.getCorpToken(), customerId, statffId);
            if(sr.isSuccess()){
                return sr;
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("statffId", statffId)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("statffId", statffId)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<ResultWrapper<String>> getCustomIdList(String suiteKey, String corpId, Integer start, Integer offset) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("start", start),
                LogFormatter.KeyValue.getNew("offset", offset)
        ));
        try{
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<ResultWrapper<String>> sr = crmOapiRequestHelper.getCrmCustomerIdList(suiteKey, corpId, corpTokenVO.getCorpToken(), start, offset);
            return sr;
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("start", start),
                    LogFormatter.KeyValue.getNew("offset", offset)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("start", start),
                    LogFormatter.KeyValue.getNew("offset", offset)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<Void> updateFollowTime(String suiteKey, String corpId, String customerId, Long timeStamp) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("customerId", customerId),
                LogFormatter.KeyValue.getNew("timeStamp", timeStamp)
        ));
        try {
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<Void> sr = crmOapiRequestHelper.updateFollowTime(suiteKey, corpId, corpTokenVO.getCorpToken(), customerId, timeStamp);
            return sr;
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("timeStamp", timeStamp)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("timeStamp", timeStamp)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<Void> getCustomerBaseForm(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try {
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            CorpTokenVO corpTokenVO = tokenSr.getResult();
            ServiceResult<Void> sr = crmOapiRequestHelper.getCustomerBaseForm(suiteKey,corpId,corpTokenVO.getCorpToken());
            return sr;
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
