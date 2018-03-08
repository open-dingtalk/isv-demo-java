package com.dingtalk.isv.access.web.controller;

import com.dingtalk.isv.access.api.model.CorpAppVO;
import com.dingtalk.isv.access.api.model.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.LoginUserVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.EmpManageService;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.HttpResult;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifeng.zlf on 2016/2/24.
 */
@Controller
public class JsConfigController {
    private static final Logger mainLogger = LoggerFactory.getLogger(JsConfigController.class);
    private static final Logger bizLogger = LoggerFactory.getLogger("JSAPI_LOGGER");
    @Resource
    private CorpManageService corpManageService;
    @Resource
    private EmpManageService empManageService;
    //你的套件suiteKey。这里写死,ISV自己做配置
    private final String suiteKey= "suitexdhgv7mn5ufoi9ui";
    //你的微应用appid。这里写死,ISV自己做配置
    private final Long microappAppId = 1949L;
    /**
     * 测试微应用鉴权
     * @param url
     * @param corpId
     * @return
     */
    @RequestMapping("/get_js_config")
    @ResponseBody
    public Map<String, Object>  getJSConfig(@RequestParam(value = "url", required = false) String url,
                              @RequestParam(value = "corpId", required = false) String corpId

    ) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    "get_js_config",
                    LogFormatter.KeyValue.getNew("url", url),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("appId", microappAppId)
            ));
            url = check(url,corpId,suiteKey,microappAppId);
            ServiceResult<CorpJSAPITicketVO> jsapiTicketSr = corpManageService.getCorpJSAPITicket(suiteKey, corpId);
            ServiceResult<CorpAppVO> corpAppVOSr = corpManageService.getCorpApp(corpId, microappAppId);
            String nonce = com.dingtalk.oapi.lib.aes.Utils.getRandomStr(8);
            Long timeStamp = System.currentTimeMillis();
            String sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonce, timeStamp, jsapiTicketSr.getResult().getCorpJSAPITicket());
            Map<String,Object> jsapiConfig = new HashMap<String, Object>();
            jsapiConfig.put("signature",sign);
            jsapiConfig.put("nonce",nonce);
            jsapiConfig.put("timeStamp",timeStamp);
            jsapiConfig.put("agentId",corpAppVOSr.getResult().getAgentId());
            jsapiConfig.put("corpId",corpId);
            return HttpResult.getSuccess(jsapiConfig);
        }catch (Exception e){
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统错误",
                    LogFormatter.KeyValue.getNew("url", url),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ),e);
            return HttpResult.getFailure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 获取用户信息
     * @param url
     * @param corpId
     * @param code
     * @return
     */
    @RequestMapping("/get_user_info")
    @ResponseBody
    public Map<String, Object>  getUserInfo(@RequestParam(value = "url", required = false) String url,
                                            @RequestParam(value = "corpId", required = false) String corpId,
                                            @RequestParam(value = "code", required = false) String code) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    "get_user_info",
                    LogFormatter.KeyValue.getNew("code", code),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("appId", microappAppId)
            ));
            ServiceResult<LoginUserVO> userSr = empManageService.getEmpByAuthCode(suiteKey, corpId, code);
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("deviceId", userSr.getResult().getDeviceId());
            result.put("userId", userSr.getResult().getUserId());
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "get_user_info",
                    LogFormatter.KeyValue.getNew("deviceId", userSr.getResult().getDeviceId()),
                    LogFormatter.KeyValue.getNew("userId", userSr.getResult().getUserId())
            ));
            return HttpResult.getSuccess(result);
        }catch (Exception e){
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "get_user_info错误",
                    LogFormatter.KeyValue.getNew("url", url),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ),e);
            return HttpResult.getFailure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }



    private String check(String url,String corpId,String suiteKey,Long appId) throws Exception{//TODO 妈蛋的就然没有定义serviceexception
        try {
            url = URLDecoder.decode(url,"UTF-8");
            URL urler = new URL(url);
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(urler.getProtocol());
            urlBuffer.append(":");
            if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
                urlBuffer.append("//");
                urlBuffer.append(urler.getAuthority());
            }
            if (urler.getPath() != null) {
                urlBuffer.append(urler.getPath());
            }
            if (urler.getQuery() != null) {
                urlBuffer.append('?');
                urlBuffer.append(URLDecoder.decode(urler.getQuery(), "utf-8"));
            }
            url = urlBuffer.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("url非法");
        }
        return url;
    }
}
