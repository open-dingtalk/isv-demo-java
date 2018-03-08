package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.constant.CommonUtils;
import com.dingtalk.isv.access.api.model.CorpAppVO;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.message.SendMessageService;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageSendResult;
import com.dingtalk.open.client.api.service.corp.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lifeng.zlf on 2016/3/22.
 */
public class SendMessageServiceImpl implements SendMessageService {
    private static final Logger bizLogger = LoggerFactory.getLogger(SendMessageServiceImpl.class);
    private static final Logger mainLogger = LoggerFactory.getLogger(SendMessageServiceImpl.class);
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private MessageService messageService;
    @Override
    public ServiceResult<Void> sendOAMessageToUser(String suiteKey, String corpId, Long appId, String msgType, List<String> staffIdList, List<Long> deptIdList, MessageBody message){
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("appId", appId),
                LogFormatter.KeyValue.getNew("msgType", msgType),
                LogFormatter.KeyValue.getNew("staffIdList", staffIdList),
                LogFormatter.KeyValue.getNew("deptIdList", deptIdList),
                LogFormatter.KeyValue.getNew("message", JSON.toJSONString(message))
        ));
        try{
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            ServiceResult<CorpAppVO>  appSr = corpManageService.getCorpApp(corpId, appId);
            String corpToken = corpTokenSr.getResult().getCorpToken();
            MessageSendResult sr = messageService.sendToCorpConversation(corpToken, CommonUtils.stringList2String(staffIdList, "|"), CommonUtils.longList2String(deptIdList, "|"), String.valueOf(appSr.getResult().getAgentId()), "oa", message);
            if(!StringUtils.isEmpty(sr.getInvaliduser())){
                return ServiceResult.failure(ServiceResultCode.CUSTOM_FIND_ERROR.getErrCode(),ServiceResultCode.CUSTOM_FIND_ERROR.getErrMsg());
            }
            return ServiceResult.success(null);
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("appId", appId),
                    LogFormatter.KeyValue.getNew("msgType", msgType),
                    LogFormatter.KeyValue.getNew("staffIdList", staffIdList),
                    LogFormatter.KeyValue.getNew("deptIdList", deptIdList),
                    LogFormatter.KeyValue.getNew("message", JSON.toJSONString(message))
            ));
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("appId", appId),
                    LogFormatter.KeyValue.getNew("msgType", msgType),
                    LogFormatter.KeyValue.getNew("staffIdList", staffIdList),
                    LogFormatter.KeyValue.getNew("deptIdList", deptIdList),
                    LogFormatter.KeyValue.getNew("message", JSON.toJSONString(message))
            ));
            return ServiceResult.failure(ServiceResultCode.CUSTOM_FIND_ERROR.getErrCode(),ServiceResultCode.CUSTOM_FIND_ERROR.getErrMsg());
        }

    }
}
