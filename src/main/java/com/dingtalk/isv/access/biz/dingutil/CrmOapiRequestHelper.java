package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.dingtalk.isv.access.api.model.corp.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.crm.CrmContactVO;
import com.dingtalk.isv.access.api.model.crm.CrmCustomerVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteCallBackVO;
import com.dingtalk.isv.access.biz.suite.model.CorpSuiteCallBackDO;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ResultWrapper;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.isv.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

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



    public ServiceResult<CrmCustomerVO> getCrmCustomer(String suiteKey, String corpId, String accessToken, String customerId) {
        try {
            String url = getOapiDomain() + "/crm/customer/info?access_token=" + accessToken + "&customerId=" + customerId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                CrmCustomerVO crmCustomerVO = new CrmCustomerVO();
                crmCustomerVO.setCustomerId(jsonObject.getString("customerId"));
                crmCustomerVO.setAddress(jsonObject.getString("address"));
                crmCustomerVO.setDescription(jsonObject.getString("description"));
                crmCustomerVO.setMemberCount(jsonObject.getInteger("memberCount"));
                crmCustomerVO.setPhone(jsonObject.getString("phone"));
                crmCustomerVO.setName(jsonObject.getString("customerName"));
                crmCustomerVO.setFormData(jsonObject.getString("formData"));
                crmCustomerVO.setCorpId(jsonObject.getString("corpId"));
                JSONArray staffIdArr = jsonObject.getJSONArray("followStaffIds");
                List<String> staffIdList = new ArrayList<String>();
                for (int i = 0; i < staffIdArr.size(); i++) {
                    staffIdList.add(staffIdArr.getString(i));
                }
                crmCustomerVO.setFollowStaffIds(staffIdList);
                return ServiceResult.success(crmCustomerVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (IOException e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    public ServiceResult<Void> removeCrmCustomer(String suiteKey, String corpId, String accessToken, String customerId) {
        try {
            String url = getOapiDomain() + "/crm/customer/remove?access_token=" + accessToken + "&customerId=" + customerId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                return ServiceResult.success(null);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    public ServiceResult<List<CrmContactVO>> getCrmContactList(String suiteKey, String corpId, String accessToken, String customerId) {
        try {
            List<CrmContactVO> crmContactVOList = new ArrayList<CrmContactVO>();
            String url = getOapiDomain() + "/crm/contact/list?access_token=" + accessToken + "&customerId=" + customerId + "&offset=0&size=50";
            String sr = httpRequestHelper.doHttpGet(url);
            bizLogger.info(JSON.toJSONString(sr));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                JSONArray crmContactArray = jsonObject.getJSONArray("crmContactList");
                for (int i = 0; i < crmContactArray.size(); i++) {
                    JSONObject crmContactObject = crmContactArray.getJSONObject(i);
                    CrmContactVO crmContactVO = new CrmContactVO();
                    crmContactVO.setContactId(crmContactObject.getString("contactId"));
                    crmContactVO.setMobile(crmContactObject.getString("mobile"));
                    crmContactVO.setStateCode(crmContactObject.getString("stateCode"));
                    crmContactVO.setName(crmContactObject.getString("contactName"));
                    crmContactVO.setFormData(crmContactObject.getString("formData"));
                    crmContactVO.setCorpId(crmContactObject.getString("corpId"));
                    crmContactVOList.add(crmContactVO);
                }
                return ServiceResult.success(crmContactVOList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (IOException e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    public ServiceResult<CrmContactVO> getCrmContact(String suiteKey, String corpId, String accessToken, String customerId,String contactId) {
        try {
            String url = getOapiDomain() + "/crm/contact/info?access_token=" + accessToken + "&customerId=" + customerId + "&contactId="+contactId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                CrmContactVO crmContactVO = new CrmContactVO();
                crmContactVO.setCorpId(jsonObject.getString("corpId"));
                crmContactVO.setContactId(jsonObject.getString("contactId"));
                crmContactVO.setCustomerId(jsonObject.getString("customerId"));
                crmContactVO.setMobile(jsonObject.getString("mobile"));
                crmContactVO.setStateCode(jsonObject.getString("stateCode"));
                crmContactVO.setName(jsonObject.getString("contactName"));
                crmContactVO.setFormData(jsonObject.getString("formData"));
                return ServiceResult.success(crmContactVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (IOException e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("customerId", customerId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 为一个客户绑定跟进人
     *
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @param customerId
     * @param staffId
     * @return
     */
    public ServiceResult<Void> bindCustom(String suiteKey, String corpId, String accessToken, String customerId, String staffId) {
        try {
            String url = getOapiDomain() + "/crm/follower/bind?access_token=" + accessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("customerId", customerId);
            parmMap.put("followerId", staffId);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
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
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("customerId", customerId),
                    LogFormatter.KeyValue.getNew("staffId", staffId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    public ServiceResult<Void> saveCorpCallback(String suiteKey, String corpId, String accessToken, String token, String aesKey, String callBakUrl, List<String> tagList) {
        try {
            String url = getOapiDomain() + "/call_back/register_call_back?access_token=" + accessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("url", callBakUrl);
            parmMap.put("call_back_tag", tagList);
            parmMap.put("token", token);
            parmMap.put("aes_key", aesKey);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                return ServiceResult.success(null);
            }
            if(Long.valueOf(71006).equals(errCode)){
                return ServiceResult.failure(ServiceResultCode.CORP_SUITE_CALLBACK_EXIST.getErrCode(), ServiceResultCode.CORP_SUITE_CALLBACK_EXIST.getErrMsg());
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("callBakUrl", callBakUrl),
                    LogFormatter.KeyValue.getNew("token", token),
                    LogFormatter.KeyValue.getNew("aesKey", aesKey),
                    LogFormatter.KeyValue.getNew("tagList", tagList)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    public ServiceResult<CorpSuiteCallBackVO> getCorpSuiteCallback(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/call_back/get_call_back?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                JSONArray callBackArr =  jsonObject.getJSONArray("call_back_tag");
                List<String> callBackTagList = new ArrayList<String>();
                for(int i=0;i<callBackArr.size();i++){
                    String callBackTag = callBackArr.getString(i);
                    callBackTagList.add(callBackTag);
                }
                CorpSuiteCallBackVO corpSuiteCallBackVO = new CorpSuiteCallBackVO();
                corpSuiteCallBackVO.setCorpId(corpId);
                corpSuiteCallBackVO.setSuiteKey(suiteKey);
                corpSuiteCallBackVO.setCallbackTagList(callBackTagList);
                return ServiceResult.success(corpSuiteCallBackVO);
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

    public ServiceResult<Void> deleteCorpSuiteCallback(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/call_back/delete_call_back?access_token=" + accessToken;
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


    public ServiceResult<Void> updateCorpSuiteCallback(String suiteKey, String corpId, String accessToken, String token, String aesKey, String callBakUrl, List<String> tagList) {
        try {
            String url = getOapiDomain() + "/call_back/update_call_back?access_token=" + accessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("url", callBakUrl);
            parmMap.put("call_back_tag", tagList);
            parmMap.put("token", token);
            parmMap.put("aes_key", aesKey);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
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
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("callBakUrl", callBakUrl),
                    LogFormatter.KeyValue.getNew("token", token),
                    LogFormatter.KeyValue.getNew("aesKey", aesKey),
                    LogFormatter.KeyValue.getNew("tagList", tagList)
            ), e);
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


