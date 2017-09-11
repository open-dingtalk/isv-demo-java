package com.dingtalk.isv.access.biz.event;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.enums.suite.AuthFaileType;
import com.dingtalk.isv.access.api.enums.suite.SuitePushType;
import com.dingtalk.isv.access.api.model.event.AuthChangeEvent;
import com.dingtalk.isv.access.api.model.event.CorpAuthSuiteEvent;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.biz.suite.dao.CorpSuiteAuthFaileDao;
import com.dingtalk.isv.access.biz.suite.model.CorpSuiteAuthFaileDO;
import com.dingtalk.isv.common.event.EventListener;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lifeng.zlf on 2016/3/23.
 */
public class CorpAuthSuiteEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger("EVENT_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(CorpAuthSuiteEventListener.class);
    @Autowired
    private CorpSuiteAuthService corpSuiteAuthService;
    @Autowired
    private CorpSuiteAuthFaileDao corpSuiteAuthFaileDao;

    /**
     * 企业授权套件临时授权码异步逻辑
     * @param corpAuthSuiteEvent
     */
    @Subscribe
    public void listenCorpAuthSuiteEvent(CorpAuthSuiteEvent corpAuthSuiteEvent) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("corpAuthSuiteEvent", JSON.toJSONString(corpAuthSuiteEvent))
            ));
            //激活
            ServiceResult<Void> activeAppSr = corpSuiteAuthService.activeCorpApp(corpAuthSuiteEvent.getSuiteKey(), corpAuthSuiteEvent.getCorpId(),corpAuthSuiteEvent.getPermanentCode());
            if(!activeAppSr.isSuccess()){
                //加入失败job,失败任务会重试
                CorpSuiteAuthFaileDO corpSuiteAuthFaileDO = new CorpSuiteAuthFaileDO();
                corpSuiteAuthFaileDO.setSuiteKey(corpAuthSuiteEvent.getSuiteKey());
                corpSuiteAuthFaileDO.setCorpId(corpAuthSuiteEvent.getCorpId());
                corpSuiteAuthFaileDO.setAuthFaileType(AuthFaileType.ACTIVE_CORP_APP_FAILE);
                corpSuiteAuthFaileDO.setSuitePushType(SuitePushType.TMP_AUTH_CODE);
                corpSuiteAuthFaileDao.addOrUpdateCorpSuiteAuthFaileDO(corpSuiteAuthFaileDO);
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("corpAuthSuiteEvent", JSON.toJSONString(corpAuthSuiteEvent))
            ), e);
        }

    }


    @Subscribe
    public void listenAuthChangeEvent(AuthChangeEvent authChangeEvent) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("authChangeEvent", JSON.toJSONString(authChangeEvent))
            ));
            //激活
            ServiceResult<Void> activeAppSr = corpSuiteAuthService.activeCorpApp(authChangeEvent.getSuiteKey(), authChangeEvent.getCorpId(),authChangeEvent.getPermanentCode());
            if(!activeAppSr.isSuccess()){
                //加入失败job
                CorpSuiteAuthFaileDO corpSuiteAuthFaileDO = new CorpSuiteAuthFaileDO();
                corpSuiteAuthFaileDO.setSuiteKey(authChangeEvent.getSuiteKey());
                corpSuiteAuthFaileDO.setCorpId(authChangeEvent.getCorpId());
                corpSuiteAuthFaileDO.setAuthFaileType(AuthFaileType.ACTIVE_CORP_APP_FAILE);
                corpSuiteAuthFaileDO.setSuitePushType(SuitePushType.TMP_AUTH_CODE);
                corpSuiteAuthFaileDao.addOrUpdateCorpSuiteAuthFaileDO(corpSuiteAuthFaileDO);
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("corpAuthSuiteEvent", JSON.toJSONString(authChangeEvent))
            ), e);
        }

    }



}
