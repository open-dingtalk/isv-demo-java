package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.LoginUserVO;
import com.dingtalk.isv.access.api.model.EmpVO;
import com.dingtalk.isv.access.api.model.OALoginUserVO;
import com.dingtalk.isv.access.api.model.CorpSuiteCallBackVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开放平台企业通讯录接口封装
 * 包括部门人员
 * Created by lifeng.zlf on 2016/1/21.
 */
public class CorpOapiRequestHelper {
    private static Logger mainLogger = LoggerFactory.getLogger(CorpOapiRequestHelper.class);
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
     * 获取企业管理员
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @return
     */
    public ServiceResult<List<EmpVO>> getCorpAdmin(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/user/get_admin?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                List<EmpVO> empVOList = new ArrayList<EmpVO>();
                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject userObject = jsonArray.getJSONObject(i);
                    EmpVO empVO = new EmpVO();
                    empVO.setActive(userObject.getBoolean("active"));
                    empVO.setAvatar(userObject.getString("avatar"));
                    empVO.setDepartment(JSONArray.parseArray(userObject.getJSONArray("department").toJSONString(),Long.class));
                    empVO.setDingId(userObject.getString("dingId"));
                    empVO.setIsAdmin(userObject.getBoolean("isAdmin"));
                    empVO.setIsBoss(userObject.getBoolean("isBoss"));
                    empVO.setIsHide(userObject.getBoolean("isHide"));
                    empVO.setIsLeaderInDepts((Map)JSON.parseObject(userObject.getString("isLeaderInDepts")));
                    empVO.setIsSuper(userObject.getBoolean("isSuper"));
                    empVO.setJobnumber(userObject.getString("jobnumber"));
                    empVO.setName(userObject.getString("name"));
                    empVO.setOrderInDepts((Map)JSON.parseObject(userObject.getString("orderInDepts")));
                    empVO.setPosition(userObject.getString("position"));
                    empVO.setStaffId(userObject.getString("userid"));
                    empVOList.add(empVO);
                }
                return ServiceResult.success(empVOList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 获取注册企业的回调信息
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @return
     */
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
                corpSuiteCallBackVO.setCallbackUrl(jsonObject.getString("url"));
                corpSuiteCallBackVO.setToken(jsonObject.getString("token"));
                corpSuiteCallBackVO.setAesKey(jsonObject.getString("aes_key"));
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

    /**
     * 注册授权企业的企业信息变更回调地址。企业数据发生变更时候,钉钉开放平台会向注册的URL地址POST数据
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param token         用于解密钉钉开放平台推送数据的解密Token
     * @param aesKey        用于解密钉钉开放平台推送数据的解密AesKey
     * @param callBakUrl    用于接收钉钉开放平台推送数据的URL
     * @param tagList       注册回调事件的事件列表。
     */
    public ServiceResult<Void> registerCorpCallback(String suiteKey, String corpId, String accessToken, String token, String aesKey, String callBakUrl, List<String> tagList) {
        bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("accessToken", accessToken),
                LogFormatter.KeyValue.getNew("callBakUrl", callBakUrl),
                LogFormatter.KeyValue.getNew("token", token),
                LogFormatter.KeyValue.getNew("aesKey", aesKey),
                LogFormatter.KeyValue.getNew("tagList", tagList)
        ));
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
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"+e.toString()
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 更新授权企业的企业信息变更回调地址。企业数据发生变更时候,钉钉开放平台会向注册的URL地址POST数据
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param token         用于解密钉钉开放平台推送数据的解密Token
     * @param aesKey        用于解密钉钉开放平台推送数据的解密AesKey
     * @param callBakUrl    用于接收钉钉开放平台推送数据的URL
     * @param tagList       注册回调事件的事件列表。
     */
    public ServiceResult<Void> updateCorpCallback(String suiteKey, String corpId, String accessToken, String token, String aesKey, String callBakUrl, List<String> tagList) {
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
     * 删除授权企业的回调。
     * 在企业对套件解除授权的时候,钉钉会自动删除ISV注册的企业回调。
     * 因为在套件解除授权的时候,这个接口已经不能调用了。因为ISV无法在使用企业授权调用开放平台接口了
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @return
     */
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


    /**
     * 免登,根据code获取用户信息
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业CorpId
     * @param accessToken   授权企业AccessToken
     * @param code          授权企业员工免登Code
     */
    public ServiceResult<LoginUserVO> getEmpByAuthCode(String suiteKey, String corpId, String accessToken,String code) {
        try {
            String url = getOapiDomain() + "/user/getuserinfo?access_token=" + accessToken+"&code="+code;
            String result = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(result);
            Long errCode = jsonObject.getLong("errcode");
            if (errCode == 0) {
                LoginUserVO loginUserVO = new LoginUserVO();
                loginUserVO.setCorpId(corpId);
                loginUserVO.setDeviceId(jsonObject.getString("deviceId"));
                loginUserVO.setUserId(jsonObject.getString("userid"));
                loginUserVO.setIsSys(jsonObject.getBoolean("is_sys"));
                loginUserVO.setSysLevel(jsonObject.getInteger("sys_level"));
                return ServiceResult.success(loginUserVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "免登CODE换取用户信息失败",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("code", code)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 根据SSOCode来换取用户在钉钉OA后台的免登身份信息
     * @param isvCorpId ISV企业CorpId
     * @param ssoToken  ISV企业SSOToken
     * @param ssoCode   OA后台免登临时授权码
     * @return
     */
    public ServiceResult<OALoginUserVO> getEmpBySSOCode(String isvCorpId, String ssoToken,String ssoCode) {
        try {
            String url = getOapiDomain() + "/sso/getuserinfo?access_token=" + ssoToken+"&code="+ssoCode;
            String result = httpRequestHelper.doHttpGet(url);
            System.err.println(result);
            JSONObject jsonObject = JSON.parseObject(result);
            Long errCode = jsonObject.getLong("errcode");
            if (errCode == 0) {
                JSONObject userObject = jsonObject.getJSONObject("user_info");
                JSONObject corpObject = jsonObject.getJSONObject("corp_info");
                OALoginUserVO oaLoginUserVO = new OALoginUserVO();
                oaLoginUserVO.setUserId(userObject.getString("userid"));
                oaLoginUserVO.setAvatar(userObject.getString("avatar"));
                oaLoginUserVO.setEmail(userObject.getString("email"));
                oaLoginUserVO.setName(userObject.getString("name"));
                oaLoginUserVO.setCorpId(corpObject.getString("corpid"));
                oaLoginUserVO.setCorpName(corpObject.getString("corp_name"));
                return ServiceResult.success(oaLoginUserVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "免登CODE换取用户信息失败",
                    LogFormatter.KeyValue.getNew("isvCorpId", isvCorpId),
                    LogFormatter.KeyValue.getNew("ssoToken", ssoToken),
                    LogFormatter.KeyValue.getNew("ssoCode", ssoCode)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }




}


