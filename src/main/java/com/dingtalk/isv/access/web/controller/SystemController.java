package com.dingtalk.isv.access.web.controller;

import com.dingtalk.isv.access.api.constant.AccessSystemConfig;
import com.dingtalk.isv.access.api.model.corp.CorpAppVO;
import com.dingtalk.isv.access.api.model.corp.CorpJSAPITicketVO;
import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.event.mq.SuiteCallBackMessage;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteCallBackVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.corp.dao.CorpJSAPITicketDao;
import com.dingtalk.isv.access.biz.corp.model.helper.CorpJSAPITicketConverter;
import com.dingtalk.isv.access.biz.dingutil.ConfOapiRequestHelper;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.isv.common.util.HttpUtils;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 这个controller功能如下
 * 1.健康检测
 * 2.数据订正
 * 3.临时测试
 */
@Controller
public class SystemController {
    @Autowired
    private EventBus corpAuthSuiteEventBus;



    @RequestMapping("/test")
    @ResponseBody
    public String checkPreload() {
        corpAuthSuiteEventBus.post(new Integer(100));
        corpAuthSuiteEventBus.post(new Integer(200));
        corpAuthSuiteEventBus.post(new Integer(300));
        return "success";
    }

    @Resource
    private CorpSuiteAuthService corpSuiteAuthService;
    @Resource
    private CorpManageService corpManageService;
    @Resource
    SuiteManageService suiteManageService;
    @Resource
    AccessSystemConfig accessSystemConfig;
    @Resource
    CorpJSAPITicketDao corpJSAPITicketDao;
    @Resource
    ConfOapiRequestHelper confOapiRequestHelper;
    @ResponseBody
    @RequestMapping(value = "/token/{suiteKey}", method = {RequestMethod.GET})
    public String genToken( HttpServletRequest request,@PathVariable("suiteKey") String suiteKey ) {
        String ip = HttpUtils.getRemortIP(request);
        if("127.0.0.1".equals(ip)){
            ServiceResult<Void> sr = suiteManageService.saveOrUpdateSuiteToken(suiteKey);
            if(sr.isSuccess()){
                return "success";
            }
            return sr.getCode()+","+sr.getMessage();
        }
        return "is not 127.0.0.1";
    }


    @RequestMapping(value = "/getCorpToken", method = {RequestMethod.GET})
    @ResponseBody
    public String getAccessToken(HttpServletRequest request,
                                  @RequestParam(value = "corpId", required = true) String corpId,
                                  @RequestParam(value = "suiteKey", required = true) String suiteKey) {
        String ip = HttpUtils.getRemortIP(request);
        if("127.0.0.1".equals(ip)){
            ServiceResult<CorpTokenVO> sr = corpManageService.getCorpToken(suiteKey, corpId);
            if(sr.isSuccess()){
                return "success";
            }
            return sr.getCode()+","+sr.getMessage();
        }
        return "is not 127.0.0.1";
    }


    @RequestMapping(value = "/getJsTicket", method = {RequestMethod.GET})
    @ResponseBody
    public String getJsTicket(HttpServletRequest request,
                                 @RequestParam(value = "corpId", required = true) String corpId,
                                 @RequestParam(value = "suiteKey", required = true) String suiteKey) {
        String ip = HttpUtils.getRemortIP(request);
        if("127.0.0.1".equals(ip)){
            ServiceResult<CorpTokenVO> corpTokenVoSr = corpManageService.getCorpToken(suiteKey, corpId);
            ServiceResult<CorpJSAPITicketVO> jsAPITicketSr = confOapiRequestHelper.getJSTicket(suiteKey, corpId, corpTokenVoSr.getResult().getCorpToken());
            corpJSAPITicketDao.saveOrUpdateCorpJSAPITicket(CorpJSAPITicketConverter.corpJSTicketVO2CorpJSTicketDO(jsAPITicketSr.getResult()));
            return "success";
        }
        return "is not 127.0.0.1";
    }


    @RequestMapping(value = "/getSuiteToken", method = {RequestMethod.GET})
    @ResponseBody
    public String getSuiteAccessToken(HttpServletRequest request,
                                      @RequestParam(value = "suiteKey", required = true) String suiteKey) {
        String ip = HttpUtils.getRemortIP(request);
        if("127.0.0.1".equals(ip)){
            ServiceResult<Void> sr = suiteManageService.saveOrUpdateSuiteToken(suiteKey);
            if(sr.isSuccess()){
                return "success";
            }
            return sr.getCode()+","+sr.getMessage();
        }
        return "is not 127.0.0.1";
    }



    /**
     * 更新企业回调地址
     * @param request
     * @param suiteKey
     * @return
     */
    @RequestMapping(value = "/updateCorpCallBack", method = {RequestMethod.GET})
    @ResponseBody
    public String updateCorpCallBack(HttpServletRequest request,
                                    @RequestParam(value = "suiteKey", required = true) String suiteKey,
                                    @RequestParam(value = "corpId", required = true) String corpId
                                    ) {
        String ip = HttpUtils.getRemortIP(request);
        if("127.0.0.1".equals(ip)){
            //订正全体
            if(StringUtils.isEmpty(corpId)){
                ServiceResult<List<CorpSuiteAuthVO>> sr = corpSuiteAuthService.getCorpSuiteAuthByPage(suiteKey, 0, Integer.MAX_VALUE);
                List<CorpSuiteAuthVO> corpSuiteAuthVOList = sr.getResult();
                for(CorpSuiteAuthVO corpSuiteAuthVO:corpSuiteAuthVOList){
                    ServiceResult<CorpSuiteCallBackVO> callBackSr =  corpSuiteAuthService.getCorpCallback(suiteKey, corpSuiteAuthVO.getCorpId());
                    if(callBackSr.isSuccess()){
                        corpSuiteAuthService.updateCorpCallback(suiteKey, corpSuiteAuthVO.getCorpId(), (accessSystemConfig.getCorpSuiteCallBackUrl() + suiteKey), SuiteCallBackMessage.Tag.getAllTag());
                        continue;
                    }
                    corpSuiteAuthService.saveCorpCallback(suiteKey, corpSuiteAuthVO.getCorpId(), (accessSystemConfig.getCorpSuiteCallBackUrl()+suiteKey), SuiteCallBackMessage.Tag.getAllTag());
                }
            }else{
                ServiceResult<CorpSuiteCallBackVO> callBackSr =  corpSuiteAuthService.getCorpCallback(suiteKey, corpId);
                if(callBackSr.isSuccess()){
                    corpSuiteAuthService.updateCorpCallback(suiteKey, corpId, (accessSystemConfig.getCorpSuiteCallBackUrl() + suiteKey), SuiteCallBackMessage.Tag.getAllTag());
                }
                corpSuiteAuthService.saveCorpCallback(suiteKey, corpId, (accessSystemConfig.getCorpSuiteCallBackUrl()+suiteKey), SuiteCallBackMessage.Tag.getAllTag());
            }
            return "success";
        }
        return "is not 127.0.0.1";
    }







}
