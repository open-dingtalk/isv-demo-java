package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.ISVBizLockVO;
import com.dingtalk.isv.access.api.model.SuiteTicketVO;
import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.SuiteVO;
import com.dingtalk.isv.access.api.service.ISVBizLockService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.dao.SuiteDao;
import com.dingtalk.isv.access.biz.dao.SuiteTicketDao;
import com.dingtalk.isv.access.biz.dao.SuiteTokenDao;
import com.dingtalk.isv.access.biz.model.SuiteDO;
import com.dingtalk.isv.access.biz.model.SuiteTicketDO;
import com.dingtalk.isv.access.biz.model.SuiteTokenDO;
import com.dingtalk.isv.access.biz.model.converter.SuiteConverter;
import com.dingtalk.isv.access.biz.model.converter.SuiteTicketConvert;
import com.dingtalk.isv.access.biz.model.converter.SuiteTokenConverter;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.isv.SuiteToken;
import com.dingtalk.open.client.api.service.isv.IsvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

public class SuiteManageServiceImpl implements SuiteManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("SUITE_MANAGE_LOGGER");
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteManageServiceImpl.class);
    @Resource
    private SuiteDao suiteDao;
    @Resource
    private SuiteTokenDao suiteTokenDao;
    @Resource
    private SuiteTicketDao suiteTicketDao;
    @Resource
    private IsvService isvService;
    @Resource
    private ISVBizLockService isvBizLockService;

    @Override
    public ServiceResult<Void> addSuite(SuiteVO suiteVO) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteVO", JSON.toJSONString(suiteVO))
        ));
        try{
            SuiteDO suiteDO = SuiteConverter.SuiteVO2SuiteDO(suiteVO);
            suiteDao.addSuite(suiteDO);
            return  ServiceResult.success(null);
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteVO", JSON.toJSONString(suiteVO))
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<SuiteVO> getSuiteByKey(String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try{
            SuiteDO suiteDO =  suiteDao.getSuiteByKey(suiteKey);
            if(null==suiteDO){
                return ServiceResult.success(null);
            }
            SuiteVO suiteVO = SuiteConverter.SuiteDO2SuiteVO(suiteDO);
            return  ServiceResult.success(suiteVO);
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 请求钉钉开放平台获取套件的SuiteToken
     * @param suiteKey  套件SuiteKey
     */
    private ServiceResult<SuiteTokenVO> saveOrUpdateSuiteToken(String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        //生成一个唯一索引的Key。作为锁
        String suiteTokenLockKey = "suite_token_"+suiteKey;
        try{
            //加锁,防止出现多个线程同时获取更新suitetoken,导致本地的suitetoken是不可用的
            ServiceResult<ISVBizLockVO> lockSr = isvBizLockService.getISVBizLock(suiteTokenLockKey,new Date(System.currentTimeMillis()+1000*60));
            if(lockSr.isSuccess()){
                SuiteDO suiteDO = suiteDao.getSuiteByKey(suiteKey);
                SuiteTicketDO suiteTicketDO = suiteTicketDao.getSuiteTicketByKey(suiteKey);
                //使用SDK的方式请求开放平台
                SuiteToken suiteToken = isvService.getSuiteToken(suiteDO.getSuiteKey(), suiteDO.getSuiteSecret(), suiteTicketDO.getSuiteTicket());

                SuiteTokenDO suiteTokenDO = new SuiteTokenDO();
                suiteTokenDO.setGmtCreate(new Date());
                suiteTokenDO.setGmtModified(new Date());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND,(int)suiteToken.getExpires_in());
                suiteTokenDO.setExpiredTime(calendar.getTime());//记录套件SuiteToken的超时时间
                suiteTokenDO.setSuiteKey(suiteKey);
                suiteTokenDO.setSuiteToken(suiteToken.getSuite_access_token());
                suiteTokenDao.saveOrUpdateSuiteToken(suiteTokenDO);
                suiteTokenDO = suiteTokenDao.getSuiteTokenByKey(suiteKey);
                return ServiceResult.success(SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(suiteTokenDO));
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
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),"获取锁失败");
            }
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "程序异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }finally {
            //解锁
            isvBizLockService.removeISVBizLock(suiteTokenLockKey);
        }
    }

    @Override
    public ServiceResult<SuiteTokenVO> getSuiteToken(String suiteKey) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            SuiteTokenDO suiteTokenDO = suiteTokenDao.getSuiteTokenByKey(suiteKey);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);
            //如果套件的SuiteToken不存在,或者快要过期了。
            //为了防止误差,提前10分钟更新corptoken
            if (null == suiteTokenDO || calendar.getTime().compareTo(suiteTokenDO.getExpiredTime()) != -1) {
                //直接请求开放平台更新套件Token
                return saveOrUpdateSuiteToken(suiteKey);
            }
            SuiteTokenVO suiteTokenVO =  SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(suiteTokenDO);
            return ServiceResult.success(suiteTokenVO);
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<Void> saveOrUpdateSuiteTicket(SuiteTicketVO suiteTicketVO) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteTicketVO", JSON.toJSONString(suiteTicketVO))
        ));
        try{
            SuiteTicketDO suiteTicketDO = SuiteTicketConvert.suiteTicketVO2SuiteTicketDO(suiteTicketVO);
            suiteTicketDao.saveOrUpdateSuiteTicket(suiteTicketDO);
            return ServiceResult.success(null);
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteTicketVO", JSON.toJSONString(suiteTicketVO))
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
