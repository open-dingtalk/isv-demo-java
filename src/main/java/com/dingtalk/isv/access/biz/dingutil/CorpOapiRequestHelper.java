package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.corp.LoginUserVO;
import com.dingtalk.isv.access.api.model.corp.StaffVO;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.isv.common.util.HttpRequestHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * 开放平台企业通讯录接口封装
 * 包括部门人员
 * Created by lifeng.zlf on 2016/1/21.
 */
public class CorpOapiRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(CorpOapiRequestHelper.class);
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
     * 暂未开放
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @return
     */
    @Deprecated
    public ServiceResult<List<StaffVO>> getAdmin(String suiteKey, String corpId, String accessToken) {
        try {
            String url = getOapiDomain() + "/user/get_admin?access_token=" + accessToken;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                List<StaffVO> staffVOList = new ArrayList<StaffVO>();
                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject userObject = jsonArray.getJSONObject(i);
                    StaffVO staffVO = new StaffVO();
                    staffVO.setActive(userObject.getBoolean("active"));
                    staffVO.setAvatar(userObject.getString("avatar"));
                    staffVO.setDepartment(JSONArray.parseArray(userObject.getJSONArray("department").toJSONString(),Long.class));
                    staffVO.setDingId(userObject.getString("dingId"));
                    staffVO.setIsAdmin(userObject.getBoolean("isAdmin"));
                    staffVO.setIsBoss(userObject.getBoolean("isBoss"));
                    staffVO.setIsHide(userObject.getBoolean("isHide"));
                    staffVO.setIsLeaderInDepts((Map)JSON.parseObject(userObject.getString("isLeaderInDepts")));
                    staffVO.setIsSuper(userObject.getBoolean("isSuper"));
                    staffVO.setJobnumber(userObject.getString("jobnumber"));
                    staffVO.setName(userObject.getString("name"));
                    staffVO.setOrderInDepts((Map)JSON.parseObject(userObject.getString("orderInDepts")));
                    staffVO.setPosition(userObject.getString("position"));
                    staffVO.setStaffId(userObject.getString("userid"));
                    staffVOList.add(staffVO);
                }
                return ServiceResult.success(staffVOList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (IOException e) {
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
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @param code
     * @return
     */
    public ServiceResult<LoginUserVO> getStaffByAuthCode(String suiteKey, String corpId, String accessToken,String code) {
        try {
            String url = getOapiDomain() + "/user/getuserinfo?access_token=" + accessToken+"&code="+code;
            url = MessageFormat.format(url, accessToken, code);
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
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken),
                    LogFormatter.KeyValue.getNew("code", code)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

}


