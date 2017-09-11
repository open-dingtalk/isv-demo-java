package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.corp.FollowerSimpleVO;
import com.dingtalk.isv.access.api.model.suite.UnActiveCorpVO;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.isv.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ISV授权流程相关接口
 * Created by lifeng.zlf on 2016/4/27.
 */
public class ISVRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(ISVRequestHelper.class);
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
     * 获取套件中的微应用开通未成功的企业
     * @param suiteAccessToken
     * @param appId
     * @return
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
        } catch (Throwable e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken),
                    LogFormatter.KeyValue.getNew("appId", appId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


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
        } catch (Throwable e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken),
                    LogFormatter.KeyValue.getNew("appId", appId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
}
