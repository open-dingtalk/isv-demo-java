package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ResultWrapper;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * crm业务相关接口,http封装
 * Created by lifeng.zlf on 2016/1/21.
 */
public class CrmOapiRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(CrmOapiRequestHelper.class);
    private static final Logger bizLogger = LoggerFactory.getLogger("HTTP_INVOKE_LOGGER");
    private HttpRequestHelper httpRequestHelper;
    private String oapiDomain;

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

    public ServiceResult<String> requestOapiForGet(String url) {
        try {
            String sr = httpRequestHelper.doHttpGet(getOapiDomain() + url);
            bizLogger.info(sr);
            return ServiceResult.success(sr);
        } catch (IOException e) {
            logger.error("requestOapiForGet:" + url, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }



    /**
     * 获取企业下的客户列表
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @param start
     * @param offset max200
     * @return
     */
    public ServiceResult<ResultWrapper<String>> getCrmCustomerIdList(String suiteKey, String corpId, String accessToken, Integer start, Integer offset) {
        try {
            String url = getOapiDomain() + "/crm/customer/list?access_token=" + accessToken+"&offset="+start+"&limit="+offset;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                ResultWrapper<String> resultWrapper = new ResultWrapper<String>();
                JSONArray jsonArray = jsonObject.getJSONArray("crmContactList");
                List<String> customerIdList = new ArrayList<String>();
                for(int i=0;i<jsonArray.size();i++){
                    customerIdList.add(jsonArray.getString(i));
                }
                Boolean hasMore = jsonObject.getBoolean("hasMore");
                resultWrapper.setList(customerIdList);
                resultWrapper.setHasMore(hasMore);
                return ServiceResult.success(resultWrapper);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    /**
     * 更新跟进时间
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @param customerId
     * @param timeStamp
     * @return
     */
    public ServiceResult<Void> updateFollowTime(String suiteKey, String corpId, String accessToken,String customerId,Long timeStamp) {
        try {
            String url = getOapiDomain() + "/crm/customer/followtime/update?access_token=" + accessToken+"&customerId="+customerId+"&followTime="+timeStamp;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                return ServiceResult.success(null);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    /**
     * 获取crm客户妙手的表单样式
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @return
     */
    public ServiceResult<Void> getCustomerBaseForm(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/crm/customer/form?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                return ServiceResult.success(null);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }


    }
























}


