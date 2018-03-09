package com.dingtalk.isv.access.web.controller.suite.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.enums.suite.CorpCallBackTypeEnum;
import com.dingtalk.isv.access.api.enums.suite.SuitePushType;
import com.dingtalk.isv.access.api.model.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.model.SuiteTicketVO;
import com.dingtalk.isv.access.api.model.SuiteVO;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 套件事件回调监听
 *
 */
@Controller
public class SuiteCallBackController{
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteCallBackController.class);
    private static final Logger     bizLogger = LoggerFactory.getLogger("SUITE_CALLBACK_LOGGER");
    @Resource
    private SuiteManageService suiteManageService;
    @Resource
    private CorpSuiteAuthService corpSuiteAuthService;

    /**
     * 当套件创建完毕之后,需要手动修改一下套件的回调地址。在修改套件的回调地址之前,需要在BD中插入一条记录
     *  insert into isv_suite(id, gmt_create, gmt_modified, suite_name, suite_key, suite_secret, encoding_aes_key, token, event_receive_url)
     *  values(1, '2016-03-14 18:08:09', '2016-03-14 18:08:09', '套件名称', 'suitexdhgv7mnxxxxxxxx', 'xxxxxxxxxxKBJLLPtmFmwRtKfsuiEHHpBPx8jGlCSp-iznz9gFSpkG0T0KMU9jyB',
     *  'dd18qxxxxxx357g8r7itm5pyu5hg8ibe1blhqawhuaz', 'xxxxqaz2WSX', '');
     *
     * 钉钉开放平台给ISV推送数据都会传入一下参数。ISV和套件微应用相关的逻辑只维护这一个接收推送推送的入口即可。
     * @param suiteKey       套件的SuiteKey
     * @param signature      钉钉开放平台给ISV推送时数据的数据签名
     * @param timestamp      钉钉开放平台给ISV推送时数据的时间戳
     * @param nonce          钉钉开放平台给ISV推送时数据的随机字符串
     * @param json           钉钉开放平台给ISV推送时数据的密文数据结构体
     */
    @ResponseBody
    @RequestMapping(value = "/suite/callback/{suiteKey}", method = {RequestMethod.POST})
    public  Map<String, String> receiveCallBack(@PathVariable("suiteKey") String suiteKey,
                                  @RequestParam(value = "signature", required = false) String signature,
                                  @RequestParam(value = "timestamp", required = false) String timestamp,
                                  @RequestParam(value = "nonce", required = false) String nonce,
                                  @RequestBody(required = false) JSONObject json
    ) {
        //打印LOG,用于做日志查询。线上为题定位追踪
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("signature", signature),
                LogFormatter.KeyValue.getNew("timestamp", timestamp),
                LogFormatter.KeyValue.getNew("nonce", nonce),
                LogFormatter.KeyValue.getNew("json", json)
        ));
        //查询套件信息。在程序发布之前,套件信息必须手动插入DB中
        ServiceResult<SuiteVO> suiteVOSr = suiteManageService.getSuiteByKey(suiteKey);
        SuiteVO suiteVO = suiteVOSr.getResult();
        //准备给钉钉开放平台返回的密文数据
        Map<String, String> encryptedMap = new HashMap<String, String>();
        try{
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(suiteVO.getToken(), suiteVO.getEncodingAesKey(), suiteVO.getSuiteKey());
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature,timestamp,nonce,encryptMsg);
            bizLogger.info(LogFormatter.getKVLogData(null,
                    "解密之后明文消息",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("signature", signature),
                    LogFormatter.KeyValue.getNew("timestamp", timestamp),
                    LogFormatter.KeyValue.getNew("nonce", nonce),
                    LogFormatter.KeyValue.getNew("json", json),
                    LogFormatter.KeyValue.getNew("plainText", plainText)
            ));
            //具体业务处理,返回给钉钉开放平台返回的明文数据
            String returnStr = isvCallbackEvent(plainText,suiteKey) ;
            encryptedMap = dingTalkEncryptor.getEncryptedMap(returnStr, System.currentTimeMillis(), com.dingtalk.oapi.lib.aes.Utils.getRandomStr(8));
            return encryptedMap;
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "解密失败程序异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("signature", signature),
                    LogFormatter.KeyValue.getNew("timestamp", timestamp),
                    LogFormatter.KeyValue.getNew("nonce", nonce),
                    LogFormatter.KeyValue.getNew("json", json)
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
            return encryptedMap;
        }
    }

    /**
     * 处理各种回调时间的TAG。这个维度的回调是和套件相关的
     * @param callbackMsg   钉钉开放平台给ISV套件回调的明文数据
     * @param suiteKey      套件SuiteKey
     */
    private String isvCallbackEvent(String callbackMsg,String suiteKey) {
        JSONObject callbackMsgJson = JSONObject.parseObject(callbackMsg);
        String eventType = callbackMsgJson.getString("EventType");
        //默认返回success明文。
        String responseEncryMsg = "success";
        if(SuitePushType.CHECK_CREATE_SUITE_URL.getKey().equals(eventType)){
            //创建套件逻辑
            responseEncryMsg = callbackMsgJson.getString("Random");
        }else if(SuitePushType.CHECK_UPDATE_SUITE_URL.getKey().equals(eventType)){
            //更改套件逻辑
            responseEncryMsg = callbackMsgJson.getString("Random");
        }else if(SuitePushType.SUITE_TICKET.getKey().equals(eventType)){
            //接收钉钉开放平台推送的SuiteTicket。SuiteTicket用于获取套件的SuiteToken
            String receiveSuiteTicket = callbackMsgJson.getString("SuiteTicket");
            SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
            suiteTicketVO.setSuiteTicket(receiveSuiteTicket);
            suiteTicketVO.setSuiteKey(suiteKey);
            ServiceResult<Void> sr = suiteManageService.saveOrUpdateSuiteTicket(suiteTicketVO);
            if(!sr.isSuccess()){
                responseEncryMsg = "faile";
            }
        }else if(SuitePushType.TMP_AUTH_CODE.getKey().equals(eventType)){
            //接收临时授权码。企业开通微应用会给套件推送临时授权码
            String tmpAuthCode = callbackMsgJson.getString("AuthCode");
            ServiceResult<CorpSuiteAuthVO>  sr = corpSuiteAuthService.saveOrUpdateCorpSuiteAuth(suiteKey, tmpAuthCode);
            if(!sr.isSuccess()){
                responseEncryMsg = "faile";
            }
        }else if(SuitePushType.CHANGE_AUTH.getKey().equals(eventType)){
            String corpId = callbackMsgJson.getString("AuthCorpId");
            ServiceResult<Void>  sr = corpSuiteAuthService.handleChangeAuth(suiteKey,corpId);
            if(!sr.isSuccess()){
                responseEncryMsg = "faile";
            }
        }else if(SuitePushType.SUITE_RELIEVE.getKey().equals(eventType)){
            //企业对套件解除授权。同时删除套件下的所有微应用。是物理删除
            String receiveCorpId = callbackMsgJson.getString("AuthCorpId");
            ServiceResult<Void>  sr = corpSuiteAuthService.handleRelieveAuth(suiteKey,receiveCorpId);
            if(!sr.isSuccess()){
                responseEncryMsg = "faile";
            }
        }else if(SuitePushType.CHECK_SUITE_LICENSE_CODE.getKey().equals(eventType)){
             //微应用的线下扫码可以通过验证码来开通应用
             //这个逻辑用来验证验证码
             //留给业务自行判断
            bizLogger.info(SuitePushType.CHECK_SUITE_LICENSE_CODE.getKey());
        }else{
            //当开放平台更新了新的推送类型,为了避免不认识,需要报警出来
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "无法识别的EventType",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("callbackMsg", callbackMsg)
            );
            bizLogger.error(errLog);
            mainLogger.error(errLog);
        }
        return responseEncryMsg;
    }


    /**
     * 授权ISV的企业中发生了数据变更。ISV接收企业的数据变更事件。
     * 这个维度的回调都是和授权企业相关的。
     * 如果ISV本地存储了企业通讯录的相关数据。那么用这个回调来实时更新企业的数据
     * @param suiteKey       套件的SuiteKey
     * @param signature      钉钉开放平台给ISV推送时数据的数据签名
     * @param timestamp      钉钉开放平台给ISV推送时数据的时间戳
     * @param nonce          钉钉开放平台给ISV推送时数据的随机字符串
     * @param json           钉钉开放平台给ISV推送时数据的密文数据结构体
     */
    @ResponseBody
    @RequestMapping(value = "/corp/callback/{suiteKey}", method = {RequestMethod.POST})
    public Map<String, String>  corpSuiteCallBack(@PathVariable("suiteKey") String suiteKey,
                                                @RequestParam(value = "signature", required = false) String signature,
                                                @RequestParam(value = "timestamp", required = false) String timestamp,
                                                @RequestParam(value = "nonce", required = false) String nonce,
                                                @RequestBody(required = false) JSONObject json
    ) {
        ServiceResult<SuiteVO> suiteVOSr = suiteManageService.getSuiteByKey(suiteKey);
        SuiteVO suiteVO = suiteVOSr.getResult();
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(suiteVO.getToken(), suiteVO.getEncodingAesKey(), suiteVO.getSuiteKey());
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("signature", signature),
                    LogFormatter.KeyValue.getNew("timestamp", timestamp),
                    LogFormatter.KeyValue.getNew("nonce", nonce),
                    LogFormatter.KeyValue.getNew("json", json),
                    LogFormatter.KeyValue.getNew("plainText", plainText)
            ));
            JSONObject jsonObject = JSON.parseObject(plainText);
            String eventType = jsonObject.getString("EventType");
            if("check_url".equals(eventType)){
                Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap("success", System.currentTimeMillis(), com.dingtalk.oapi.lib.aes.Utils.getRandomStr(8));
                return encryptedMap;
            }
            //处理不同的回调事件,如果不关心的就不用处理
            CorpCallBackTypeEnum tag = null;
            if(CorpCallBackTypeEnum.USER_ADD_ORG.getKey().equals(eventType)){
                tag =  CorpCallBackTypeEnum.USER_ADD_ORG;
            }else if(CorpCallBackTypeEnum.USER_LEAVE_ORG.getKey().equals(eventType)){
                tag =  CorpCallBackTypeEnum.USER_LEAVE_ORG;
            }
            Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap("success", System.currentTimeMillis(), com.dingtalk.oapi.lib.aes.Utils.getRandomStr(8));
            return encryptedMap;
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("signature", signature),
                    LogFormatter.KeyValue.getNew("timestamp", timestamp),
                    LogFormatter.KeyValue.getNew("nonce", nonce),
                    LogFormatter.KeyValue.getNew("json", json)
                    ), e);
            return null;
        }

    }
}
