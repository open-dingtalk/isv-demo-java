package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.CorpAuthInfoVO;
import com.dingtalk.isv.access.api.model.CorpChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.CorpChannelTokenVO;
import com.dingtalk.isv.access.api.model.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.CorpSuiteAuthVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 开放平台取conf或者token相关http接口封装
 */
public class ConfOapiRequestHelper {
    private static Logger mainLogger = LoggerFactory.getLogger(ConfOapiRequestHelper.class);
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
     * 获取企业的jsapi ticket
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权期的AccessToken
     * @return
     */
    public ServiceResult<CorpJSAPITicketVO> getJSTicket(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/get_jsapi_ticket?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String ticket = jsonObject.getString("ticket");
                Long expires_in = jsonObject.getLong("expires_in");
                CorpJSAPITicketVO corpJSTicketVO = new CorpJSAPITicketVO();
                corpJSTicketVO.setCorpId(corpId);
                corpJSTicketVO.setSuiteKey(suiteKey);
                corpJSTicketVO.setCorpJSAPITicket(ticket);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, expires_in.intValue());
                corpJSTicketVO.setExpiredTime(calendar.getTime());
                return ServiceResult.success(corpJSTicketVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    /**
     * 获取永久授权码
     * @param suiteKey          套件SuiteKey
     * @param tmpAuthCode       临时授权码code
     * @param suiteAccessToken  套件SuiteToken
     */
    public ServiceResult<CorpSuiteAuthVO> getPermanentCode(String suiteKey,String tmpAuthCode, String suiteAccessToken) {
        try {
            String url = getOapiDomain() + "/service/get_permanent_code?suite_access_token=" + suiteAccessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("tmp_auth_code", tmpAuthCode);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                JSONObject corpObject = jsonObject.getJSONObject("auth_corp_info");
                //服务窗永久授权码
                String chPcode = StringUtils.isEmpty(jsonObject.getString("ch_permanent_code"))?"":jsonObject.getString("ch_permanent_code");
                //微应用永久授权码
                String pCode = StringUtils.isEmpty(jsonObject.getString("permanent_code"))?"":jsonObject.getString("permanent_code");
                String corpId = corpObject.getString("corpid");
                CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
                corpSuiteAuthVO.setSuiteKey(suiteKey);
                corpSuiteAuthVO.setChPermanentCode(chPcode);
                corpSuiteAuthVO.setPermanentCode(pCode);
                corpSuiteAuthVO.setCorpId(corpId);
                return ServiceResult.success(corpSuiteAuthVO);
            }else if(Long.valueOf(40078).equals(errCode)){
                //不存在的临时授权码.这个问题可以忽略
                //因为钉钉开放平台在临时授权码没有被使用过的情况下,会连续推三次临时授权码。
                //当临时授权码换取永久授权码请求过程中在收到新的临时授权码,就会提示"不存在的临时授权码"
                return ServiceResult.success(null);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("tmpAuthCode", tmpAuthCode),
                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }



    /**
     * 获取服务窗token
     * @param suiteKey
     * @param corpId
     * @param chPermanentCode
     * @param suiteAccessToken
     * @return
     */
    public ServiceResult<CorpChannelTokenVO> getCorpChannelToken(String suiteKey, String corpId, String chPermanentCode, String suiteAccessToken) {
        try {
            String url = getOapiDomain() + "/service/get_channel_corp_token?suite_access_token=" + suiteAccessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("auth_corpid", corpId);
            parmMap.put("ch_permanent_code", chPermanentCode);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String chAccessToken = jsonObject.getString("access_token");
                Long expiresIn = jsonObject.getLong("expires_in");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, expiresIn.intValue());
                CorpChannelTokenVO corpChannelTokenVO = new CorpChannelTokenVO();
                corpChannelTokenVO.setCorpId(corpId);
                corpChannelTokenVO.setSuiteKey(suiteKey);
                corpChannelTokenVO.setCorpChannelToken(chAccessToken);
                corpChannelTokenVO.setExpiredTime(calendar.getTime());
                return ServiceResult.success(corpChannelTokenVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("accessToken", suiteAccessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }




    /**
     * 获取企业的jsapi ticket
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @return
     */
    public ServiceResult<CorpChannelJSAPITicketVO> getChannelJsapiTicket(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/channel/get_channel_jsapi_ticket?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String ticket = jsonObject.getString("ticket");
                Long expires_in = jsonObject.getLong("expires_in");
                CorpChannelJSAPITicketVO corpJSTicketVO = new CorpChannelJSAPITicketVO();
                corpJSTicketVO.setCorpId(corpId);
                corpJSTicketVO.setSuiteKey(suiteKey);
                corpJSTicketVO.setCorpChannelJSAPITicket(ticket);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, expires_in.intValue());
                corpJSTicketVO.setExpiredTime(calendar.getTime());
                return ServiceResult.success(corpJSTicketVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }




    /**
     * 获取企业的授权信息
     * @param suiteKey
     * @param corpId
     * @param suiteAccessToken
     * @return
     */
    public ServiceResult<CorpAuthInfoVO> getAuthInfo(String suiteKey, String corpId, String suiteAccessToken) {
        try {
            String url = getOapiDomain() + "/service/get_auth_info?suite_access_token=" + suiteAccessToken;

            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("suite_key", suiteKey);
            parmMap.put("auth_corpid", corpId);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));

            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
               CorpAuthInfoVO corpAuthInfoVO = JSON.parseObject(sr,CorpAuthInfoVO.class);
               return ServiceResult.success(corpAuthInfoVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }














}
