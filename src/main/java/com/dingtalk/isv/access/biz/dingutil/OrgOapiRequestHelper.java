package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.corp.CorpAuthInfoVO;
import com.dingtalk.isv.access.api.model.corp.CorpChannelJSAPITicketVO;
import com.dingtalk.isv.access.api.model.corp.CorpChannelTokenVO;
import com.dingtalk.isv.access.api.model.corp.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgChannelTokenVO;
import com.dingtalk.isv.access.api.model.org.OrgJSAPITicketVO;
import com.dingtalk.isv.access.api.model.org.OrgTokenVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.service.org.OrgManangerService;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.isv.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业相关接口
 * Created by lifeng.zlf on 2016/4/27.
 */
public class OrgOapiRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(OrgOapiRequestHelper.class);
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
     * @param corpId
     * @param accessToken
     * @return
     */
    public ServiceResult<OrgJSAPITicketVO> getOrgJSAPITicket(String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/get_jsapi_ticket?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String ticket = jsonObject.getString("ticket");
                Long expires_in = jsonObject.getLong("expires_in");
                OrgJSAPITicketVO orgJSAPITicketVO = new OrgJSAPITicketVO();
                orgJSAPITicketVO.setCorpId(corpId);
                orgJSAPITicketVO.setCorpJSAPITicket(ticket);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, expires_in.intValue());
                orgJSAPITicketVO.setExpiredTime(calendar.getTime());
                return ServiceResult.success(orgJSAPITicketVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }



    public ServiceResult<OrgTokenVO> getOrgToken(String corpId, String corpSecret) {
        try {
            String url = getOapiDomain() + "/gettoken?corpid=" + corpId+"&corpsecret="+corpSecret;
            String sr = httpRequestHelper.doHttpGet(url);
            System.err.println(sr);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String accessToken = jsonObject.getString("access_token");
                OrgTokenVO orgTokenVO = new OrgTokenVO();
                orgTokenVO.setCorpId(corpId);
                orgTokenVO.setCorpToken(accessToken);
                return ServiceResult.success(orgTokenVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("corpSecret", corpSecret)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }





    public ServiceResult<OrgChannelTokenVO> getOrgChannelToken(String corpId, String corpChannelSecret) {
        try {
            String url = getOapiDomain() + "/channel/get_channel_token?corpid=" + corpId+"&channel_secret="+corpChannelSecret;
            String sr = httpRequestHelper.doHttpGet(url);
            System.err.println(sr);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String accessToken = jsonObject.getString("access_token");
                OrgChannelTokenVO orgChannelTokenVO = new OrgChannelTokenVO();
                orgChannelTokenVO.setCorpId(corpId);
                orgChannelTokenVO.setCorpChannelToken(accessToken);
                return ServiceResult.success(orgChannelTokenVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("corpSecret", corpChannelSecret)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
















}
