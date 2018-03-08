package com.dingtalk.isv.access.api.service.message;


import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.MessageBody;

import java.util.List;

/**
 * 企业对套件授权service
 */
public interface SendMessageService {
    /**
     * 发送单人oa消息
     * @param suiteKey
     * @param corpId
     * @param appId
     * @param message
     * @return
     */
    public ServiceResult<Void> sendOAMessageToUser(String suiteKey, String corpId, Long appId, String msgType, List<String> staffIdList, List<Long> deptIdList, MessageBody message);
}
