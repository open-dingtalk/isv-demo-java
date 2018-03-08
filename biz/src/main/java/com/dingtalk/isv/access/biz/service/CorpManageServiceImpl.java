package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.*;
import com.dingtalk.isv.access.api.model.CorpChannelAppVO;
import com.dingtalk.isv.access.api.model.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.api.service.ISVBizLockService;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.dao.*;
import com.dingtalk.isv.access.biz.dingutil.ConfOapiRequestHelper;
import com.dingtalk.isv.access.biz.model.*;
import com.dingtalk.isv.access.biz.dao.CorpAppDao;
import com.dingtalk.isv.access.biz.dao.CorpChannelAppDao;
import com.dingtalk.isv.access.biz.model.CorpAppDO;
import com.dingtalk.isv.access.biz.model.CorpChannelAppDO;
import com.dingtalk.isv.access.biz.model.converter.*;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.isv.CorpAuthToken;
import com.dingtalk.open.client.api.service.isv.IsvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

public class CorpManageServiceImpl implements CorpManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("CORP_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(CorpManageServiceImpl.class);
    @Resource
    private CorpDao corpDao;
    @Resource
    private CorpTokenDao corpTokenDao;
    @Resource
    private CorpChannelTokenDao corpChannelTokenDao;
    @Autowired
    private CorpJSAPITicketDao corpJSAPITicketDao;
    @Resource
    private CorpAppDao corpAppDao;
    @Resource
    private CorpChannelJSAPITicketDao corpChannelJSAPITicketDao;
    @Resource
    private CorpChannelAppDao corpChannelAppDao;
    @Resource
    private SuiteManageService suiteManageService;
    @Resource
    private CorpSuiteAuthService corpSuiteAuthService;
    @Resource
    private IsvService isvService;
    @Resource
    private ConfOapiRequestHelper confOapiRequestHelper;
    @Resource
    private ISVBizLockService isvBizLockService;
    @Override
    public ServiceResult<Void> saveOrUpdateCorp(CorpVO corpVO) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpVO", JSON.toJSONString(corpVO))
        ));
        try {
            CorpDO corpDO = CorpConverter.CorpVO2CorpDO(corpVO);
            corpDao.saveOrUpdateCorp(corpDO);
            return ServiceResult.success(null);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpVO", corpVO)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpVO", corpVO)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 维护企业的corptoken
     *
     * @param corpTokenVO
     * @return
     */
    private ServiceResult<Void> saveOrUpdateCorpToken(CorpTokenVO corpTokenVO) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpTokenVO", JSON.toJSONString(corpTokenVO))
        ));
        try {
            CorpTokenDO corpTokenDO = CorpTokenConverter.CorpTokenVO2CorpTokenDO(corpTokenVO);
            corpTokenDao.saveOrUpdateCorpToken(corpTokenDO);
            return ServiceResult.success(null);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpTokenVO", JSON.toJSONString(corpTokenVO))
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpTokenVO", JSON.toJSONString(corpTokenVO))
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }

    }

    @Override
    public ServiceResult<CorpTokenVO> getCorpToken(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        String lockKey = "corp_token_"+suiteKey+"_"+corpId;
        try {
            CorpTokenDO corpTokenDO = corpTokenDao.getCorpToken(suiteKey, corpId);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新corptoken
            if (null == corpTokenDO || calendar.getTime().compareTo(corpTokenDO.getExpiredTime()) != -1) {
                //如果CorpToken不存在或者将要过期,那么重新请求开放平台获取一个新的CorpToken
                ServiceResult<ISVBizLockVO> lockSr = isvBizLockService.getISVBizLock(lockKey,new Date(System.currentTimeMillis()+1000*60));
                if(lockSr.isSuccess()){
                    ServiceResult<SuiteTokenVO> suiteTokenVOSr = suiteManageService.getSuiteToken(suiteKey);
                    String suiteToken = suiteTokenVOSr.getResult().getSuiteToken();
                    ServiceResult<CorpSuiteAuthVO> authSr = corpSuiteAuthService.getCorpSuiteAuth(corpId, suiteKey);
                    String permanentCode = authSr.getResult().getPermanentCode();
                    CorpAuthToken corpAuthToken = isvService.getCorpToken(suiteToken, corpId, permanentCode);
                    CorpTokenVO corpTokenVO = new CorpTokenVO();
                    corpTokenVO.setCorpId(corpId);
                    corpTokenVO.setSuiteKey(suiteKey);
                    corpTokenVO.setCorpToken(corpAuthToken.getAccess_token());
                    calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.SECOND, (int) corpAuthToken.getExpires_in());
                    corpTokenVO.setExpiredTime(calendar.getTime());
                    this.saveOrUpdateCorpToken(corpTokenVO);
                    corpTokenDO = CorpTokenConverter.CorpTokenVO2CorpTokenDO(corpTokenVO);
                }else{
                    //正常情况下不会有太多的获取锁失败的日志打出来。
                    //一旦发现获取锁失败的日志打印多,一是并发量大,二是DB异常。请开发者关注
                    String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "获取SuiteToken锁失败",
                            lockSr.getCode(),
                            lockSr.getMessage(),
                            LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                    );
                    bizLogger.error(errLog);
                    mainLogger.error(errLog);
                    return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),"获取CorpToken过程中获取锁失败");
                }
            }
            CorpTokenVO corpTokenVO = CorpTokenConverter.CorpTokenDO2CorpTokenVO(corpTokenDO);
            return ServiceResult.success(corpTokenVO);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }finally {
            isvBizLockService.removeISVBizLock(lockKey);
        }
    }

    @Override
    public ServiceResult<CorpTokenVO> deleteCorpToken(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        try {
            corpTokenDao.deleteCorpToken(suiteKey, corpId);
            return ServiceResult.success(null);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<CorpTokenVO> deleteCorpChannelToken(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        try{
            corpChannelTokenDao.deleteCorpChannelToken(suiteKey,corpId);
            return ServiceResult.success(null);
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    private ServiceResult<Void> saveOrUpdateCorpJSTicket(CorpJSAPITicketVO corpJSTicketVO) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpJSTicketVO", JSON.toJSONString(corpJSTicketVO))
        ));
        try {
            CorpJSAPITicketDO corpJSTicketDO = CorpJSAPITicketConverter.corpJSTicketVO2CorpJSTicketDO(corpJSTicketVO);
            corpJSAPITicketDao.saveOrUpdateCorpJSAPITicket(corpJSTicketDO);
            return ServiceResult.success(null);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpJSTicketVO", JSON.toJSONString(corpJSTicketVO))
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpJSTicketVO", JSON.toJSONString(corpJSTicketVO))
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<CorpJSAPITicketVO> getCorpJSAPITicket(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        String jsTicketLockKey = "jsapi_ticket_"+suiteKey+"_"+corpId;
        try {
            CorpJSAPITicketDO corpJSTicketDO = corpJSAPITicketDao.getCorpJSAPITicket(suiteKey, corpId);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新jsticket
            if (null == corpJSTicketDO || calendar.getTime().compareTo(corpJSTicketDO.getExpiredTime()) != -1) {
                //加锁,更新JSTicket。一定要加锁。否则JSTicket是会被覆盖的
                ServiceResult<ISVBizLockVO> lockSr = isvBizLockService.getISVBizLock(jsTicketLockKey,new Date(System.currentTimeMillis()+1000*60));
                if(lockSr.isSuccess()){
                    ServiceResult<CorpTokenVO> corpTokenVoSr = this.getCorpToken(suiteKey, corpId);
                    String corpToken = corpTokenVoSr.getResult().getCorpToken();
                    ServiceResult<CorpJSAPITicketVO> jsAPITicketSr = confOapiRequestHelper.getJSTicket(suiteKey, corpId, corpToken);
                    if(!jsAPITicketSr.isSuccess() || null==jsAPITicketSr.getResult()){
                        String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                                "获取JSAPITicket失败",
                                jsAPITicketSr.getCode(),
                                jsAPITicketSr.getMessage(),
                                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                        );
                        bizLogger.error(errLog);
                        mainLogger.error(errLog);
                        return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),"获取JSAPITicket失败");
                    }
                    corpJSTicketDO = CorpJSAPITicketConverter.corpJSTicketVO2CorpJSTicketDO(jsAPITicketSr.getResult());
                    corpJSAPITicketDao.saveOrUpdateCorpJSAPITicket(corpJSTicketDO);
                }else{
                    //正常情况下不会有太多的获取锁失败的日志打出来。
                    //一旦发现获取锁失败的日志打印多,一是并发量大,二是DB异常。请开发者关注
                    String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "获取JSAPITicket锁失败",
                            lockSr.getCode(),
                            lockSr.getMessage(),
                            LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                    );
                    bizLogger.error(errLog);
                    mainLogger.error(errLog);
                    return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),"获取JSAPITicket锁失败");
                }
            }
            CorpJSAPITicketVO corpJSTicketVO = CorpJSAPITicketConverter.corpJSTicketDO2CorpJSTicketVO(corpJSTicketDO);
            saveOrUpdateCorpJSTicket(corpJSTicketVO);
            return ServiceResult.success(corpJSTicketVO);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }finally {
            isvBizLockService.removeISVBizLock(jsTicketLockKey);
        }
    }


    @Override
    public ServiceResult<CorpChannelJSAPITicketVO> getCorpChannelJSAPITicket(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        try {
            CorpChannelJSAPITicketDO corpChannelJSAPITicketDO = corpChannelJSAPITicketDao.getCorpChannelJSAPITicket(suiteKey, corpId);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新jsticket
            if (null == corpChannelJSAPITicketDO || calendar.getTime().compareTo(corpChannelJSAPITicketDO.getExpiredTime()) != -1) {
                ServiceResult<CorpChannelTokenVO> corpChannelTokenVoSr = this.getCorpChannelToken(suiteKey, corpId);
                ServiceResult<CorpChannelJSAPITicketVO> channelJsAPITicketSr = confOapiRequestHelper.getChannelJsapiTicket(suiteKey, corpId, corpChannelTokenVoSr.getResult().getCorpChannelToken());
                bizLogger.info(LogFormatter.getKVLogData(null,
                        LogFormatter.KeyValue.getNew("channelJsAPITicketSr", JSON.toJSONString(channelJsAPITicketSr))
                ));
                corpChannelJSAPITicketDO = CorpChannelJSAPITicketConverter.corpChannelJSAPITicketVO2CorpChannelJSAPITicketDO(channelJsAPITicketSr.getResult());
            }
            corpChannelJSAPITicketDao.saveOrUpdateCorpChannelJSAPITicket(corpChannelJSAPITicketDO);
            CorpChannelJSAPITicketVO corpChannelJSAPITicketVO = CorpChannelJSAPITicketConverter.corpChannelJSAPITicketDO2CorpChannelJSAPITicketVO(corpChannelJSAPITicketDO);
            return ServiceResult.success(corpChannelJSAPITicketVO);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<CorpAppVO> getCorpApp(String corpId, Long appId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("appId", appId)
        ));
        try {
            CorpAppDO corpAppDO = corpAppDao.getCorpApp(corpId, appId);
            if (null == corpAppDO) {
                return ServiceResult.success(null);
            }
            CorpAppVO corpAppVO = CorpAppConverter.corpAppDO2CorpAppVO(corpAppDO);
            return ServiceResult.success(corpAppVO);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("appId", appId)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("appId", appId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<CorpChannelTokenVO> getCorpChannelToken(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        try {
            CorpChannelTokenDO corpChannelTokenDO = corpChannelTokenDao.getCorpChannelToken(suiteKey, corpId);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新corptoken
            if (null == corpChannelTokenDO || calendar.getTime().compareTo(corpChannelTokenDO.getExpiredTime()) != -1) {
                ServiceResult<SuiteTokenVO> suiteTokenVOSr = suiteManageService.getSuiteToken(suiteKey);
                String suiteToken = suiteTokenVOSr.getResult().getSuiteToken();
                ServiceResult<CorpSuiteAuthVO> authSr = corpSuiteAuthService.getCorpSuiteAuth(corpId, suiteKey);
                String chPermanentCode = authSr.getResult().getChPermanentCode();
                ServiceResult<CorpChannelTokenVO> sr = confOapiRequestHelper.getCorpChannelToken(suiteKey, corpId, chPermanentCode, suiteToken);
                corpChannelTokenDO = CorpChannelTokenConverter.corpChTokenVO2CorpChTokenDO(sr.getResult());
                corpChannelTokenDao.saveOrUpdateCorpChannelToken(corpChannelTokenDO);
                corpChannelTokenDO = corpChannelTokenDao.getCorpChannelToken(suiteKey,corpId);
            }
            CorpChannelTokenVO corpChannelTokenVO = CorpChannelTokenConverter.corpChTokenDO2CorpChTokenVO(corpChannelTokenDO);
            return ServiceResult.success(corpChannelTokenVO);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<CorpChannelAppVO> getCorpChannelApp(String corpId, Long appId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("appId", appId)
        ));
        try {
            CorpChannelAppDO corpChannelAppDO = corpChannelAppDao.getCorpChannelApp(corpId, appId);
            if (null == corpChannelAppDO) {
                return ServiceResult.success(null);
            }
            CorpChannelAppVO corpChannelAppVO = CorpChannelAppConverter.corpChannelAppDO2CorpChannelAppVO(corpChannelAppDO);
            return ServiceResult.success(corpChannelAppVO);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("appId", appId)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("appId", appId)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
