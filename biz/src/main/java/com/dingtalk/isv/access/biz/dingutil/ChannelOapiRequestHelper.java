package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.FollowerSimpleVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 开放平台取conf或者token相关http接口封装
 * Created by lifeng.zlf on 2016/4/27.
 */
public class ChannelOapiRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(ChannelOapiRequestHelper.class);
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
     * @param suiteKey
     * @param corpId
     * @param chAccessToken
     * @return
     */
    public ServiceResult<List<FollowerSimpleVO>> getChannelUserList(String suiteKey, String corpId, String chAccessToken, Integer offset, Integer size) {
        try {
            String url = getOapiDomain() + "/channel/user/list?access_token=" + chAccessToken+"&offset="+offset+"&size="+size;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                List<FollowerSimpleVO> simpleVOList = new ArrayList<FollowerSimpleVO>();
                JSONArray userArr = jsonObject.getJSONArray("userList");
                for(int i=0;i<userArr.size();i++){
                    String openId = userArr.getJSONObject(i).getString("openid");
                    String unionId = userArr.getJSONObject(i).getString("unionid");
                    FollowerSimpleVO followerSimpleVO = new FollowerSimpleVO();
                    followerSimpleVO.setOpenid(openId);
                    followerSimpleVO.setUnionid(unionId);
                    simpleVOList.add(followerSimpleVO);
                }
                return ServiceResult.success(simpleVOList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("chAccessToken", chAccessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }




    public ServiceResult<FollowerSimpleVO> getChannelUserByOpenId(String suiteKey, String corpId, String chAccessToken,String openId) {
        try {
            String url = getOapiDomain() + "/channel/user/get_by_openid?access_token=" + chAccessToken+"&openid="+openId;
            String sr = httpRequestHelper.doHttpGet(url);
            System.err.println(sr);
            JSONObject jsonObject = JSON.parseObject(sr);
            /**
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                List<FollowerSimpleVO> simpleVOList = new ArrayList<FollowerSimpleVO>();
                JSONArray userArr = jsonObject.getJSONArray("userList");
                for(int i=0;i<userArr.size();i++){
                    String openId = userArr.getJSONObject(i).getString("openid");
                    String unionId = userArr.getJSONObject(i).getString("unionid");
                    FollowerSimpleVO followerSimpleVO = new FollowerSimpleVO();
                    followerSimpleVO.setOpenid(openId);
                    followerSimpleVO.setUnionid(unionId);
                    simpleVOList.add(followerSimpleVO);
                }
                return ServiceResult.success(simpleVOList);
            }
             **/
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Throwable e) {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("chAccessToken", chAccessToken)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
}
