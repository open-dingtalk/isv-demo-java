package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.ISVSSOTokenVO;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.model.UnActiveCorpVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ISV授权流程相关接口
 */
public class ISVRequestHelper {
    private static Logger mainLogger = LoggerFactory.getLogger(ISVRequestHelper.class);
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

    /**
     * 获取开通套件微应用未成功的企业列表
     * @param suiteAccessToken  套件的SuiteToken
     * @param appId             套件下的微应用APPID
     */
    public ServiceResult<List<UnActiveCorpVO>> getUnactiveCorp(String suiteAccessToken, Long appId) {
        try {
            String url = getOapiDomain() + "/service/get_unactive_corp?suite_access_token=" + suiteAccessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("app_id", appId);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            List<UnActiveCorpVO> unActiveCorpVOList = new ArrayList<UnActiveCorpVO>();
            if (Long.valueOf(0).equals(errCode)) {
                JSONArray corpArr = jsonObject.getJSONArray("corp_list");
                for(int i=0;i<corpArr.size();i++){
                    UnActiveCorpVO unActiveCorpVO = new UnActiveCorpVO();
                    unActiveCorpVO.setAppId(appId);
                    unActiveCorpVO.setCorpId(corpArr.getString(i));
                    unActiveCorpVOList.add(unActiveCorpVO);
                }
                return ServiceResult.success(unActiveCorpVOList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken),
                    LogFormatter.KeyValue.getNew("appId", appId)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 重新为开通套件微应用不成功的企业开通
     * @param suiteAccessToken  套件SuiteToken
     * @param appId             套件下的微应用APPID
     * @param corpId            开通套件不成功的企业CORPID
     */
    public ServiceResult<Void> reAuthCorp(String suiteAccessToken, Long appId,List<String> corpId) {
        try {
            String url = getOapiDomain() + "/service/reauth_corp?suite_access_token=" + suiteAccessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("app_id", appId);
            parmMap.put("corpid_list", corpId);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                return ServiceResult.success(null);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken),
                    LogFormatter.KeyValue.getNew("appId", appId)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 获取OA后台免登的SSOToken
     * @param ssoCorpId        企业后台免登CorpId
     * @param ssoCorpSecret    企业后台免登CorpSecret
     */
    public ServiceResult<ISVSSOTokenVO> getSSOToken(String ssoCorpId, String ssoCorpSecret) {
        try {
            String url = getOapiDomain() + "/sso/gettoken?corpid=" + ssoCorpId+"&corpsecret="+ssoCorpSecret;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String accessToken = jsonObject.getString("access_token");
                ISVSSOTokenVO isvssoTokenVO = new ISVSSOTokenVO();
                isvssoTokenVO.setIsvCorpId(ssoCorpId);
                isvssoTokenVO.setIsvSsoToken(accessToken);
                isvssoTokenVO.setExpiredTime(new Date(System.currentTimeMillis()+jsonObject.getLong("expires_in")*1000));
                return ServiceResult.success(isvssoTokenVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("ssoCorpId", ssoCorpId),
                    LogFormatter.KeyValue.getNew("ssoCorpSecret", ssoCorpSecret)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    /**
     * ISV以企业身份获取自己企业的CorpToken
     * @param corpId        ISV企业的CorpId
     * @param corpSecret    ISV企业的CorpSecret
     */
    public ServiceResult<CorpTokenVO> getCorpToken(String corpId, String corpSecret) {
        try {
            String url = getOapiDomain() + "/gettoken?corpid=" + corpId+"&corpsecret="+corpSecret;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String accessToken = jsonObject.getString("access_token");
                CorpTokenVO corpTokenVO = new CorpTokenVO();
                corpTokenVO.setCorpId(corpId);
                corpTokenVO.setCorpToken(accessToken);
                corpTokenVO.setExpiredTime(new Date(System.currentTimeMillis()+jsonObject.getLong("expires_in")*1000));
                corpTokenVO.setSuiteKey(null);
                return ServiceResult.success(corpTokenVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("corpSecret", corpSecret)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
}
