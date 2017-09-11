package com.dingtalk.isv.access.biz.suite.service.impl;

import com.alibaba.fastjson.JSON;

import com.dingtalk.isv.access.api.model.suite.SuiteTicketVO;
import com.dingtalk.isv.access.api.model.suite.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.suite.dao.SuiteDao;
import com.dingtalk.isv.access.biz.suite.dao.SuiteTicketDao;
import com.dingtalk.isv.access.biz.suite.dao.SuiteTokenDao;
import com.dingtalk.isv.access.biz.suite.model.SuiteDO;
import com.dingtalk.isv.access.biz.suite.model.SuiteTicketDO;
import com.dingtalk.isv.access.biz.suite.model.SuiteTokenDO;
import com.dingtalk.isv.access.biz.suite.model.helper.SuiteConverter;
import com.dingtalk.isv.access.biz.suite.model.helper.SuiteTicketConvert;
import com.dingtalk.isv.access.biz.suite.model.helper.SuiteTokenConverter;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.isv.SuiteToken;
import com.dingtalk.open.client.api.service.isv.IsvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lifeng.zlf on 2016/1/15.
 */
public class SuiteManageServiceImpl implements SuiteManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("SUITE_MANAGE_LOGGER");
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteManageServiceImpl.class);
    @Autowired
    private SuiteDao suiteDao;
    @Autowired
    private SuiteTokenDao suiteTokenDao;
    @Autowired
    private SuiteTicketDao suiteTicketDao;
    @Autowired
    private IsvService isvService;
    @Override
    public ServiceResult<List<SuiteVO>> getAllSuite() {
        List<SuiteDO> suiteList = suiteDao.getAllSuite();
        if(CollectionUtils.isEmpty(suiteList)){
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
        List<SuiteVO> suiteVOList = new ArrayList<SuiteVO>();
        for(SuiteDO suiteDO:suiteList){
            SuiteVO suiteVO = SuiteConverter.SuiteDO2SuiteVO(suiteDO);
            suiteVOList.add(suiteVO);
        }
        return ServiceResult.success(suiteVOList);
    }

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
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteVO", JSON.toJSONString(suiteVO))
            ),e);
            mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteVO", JSON.toJSONString(suiteVO))
            ),e);
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
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            SuiteVO suiteVO = SuiteConverter.SuiteDO2SuiteVO(suiteDO);
            return  ServiceResult.success(suiteVO);
        }catch (Exception e){
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<Void> saveOrUpdateSuiteToken(String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try{
            SuiteDO suiteDO = suiteDao.getSuiteByKey(suiteKey);
            SuiteTicketDO suiteTicketDO = suiteTicketDao.getSuiteTicketByKey(suiteKey);
            SuiteToken suiteToken = isvService.getSuiteToken(suiteDO.getSuiteKey(), suiteDO.getSuiteSecret(), suiteTicketDO.getSuiteTicket());

            SuiteTokenDO suiteTokenDO = new SuiteTokenDO();
            suiteTokenDO.setGmtCreate(new Date());
            suiteTokenDO.setGmtModified(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.SECOND,(int)suiteToken.getExpires_in());
            suiteTokenDO.setExpiredTime(calendar.getTime());
            suiteTokenDO.setSuiteKey(suiteKey);
            suiteTokenDO.setSuiteToken(suiteToken.getSuite_access_token());
            suiteTokenDao.saveOrUpdateSuiteToken(suiteTokenDO);
            return ServiceResult.success(null);
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "程序异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ),e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "程序异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<List<SuiteTokenVO>> getAllSuiteToken() {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START
        ));
        List<SuiteTokenVO> resultList = new ArrayList<SuiteTokenVO>();
        List<SuiteTokenDO> suiteTokenDOList = suiteTokenDao.getAllSuiteToken();
        if (CollectionUtils.isEmpty(suiteTokenDOList)) {
            return ServiceResult.success(resultList);
        }
        for (SuiteTokenDO suiteTokenDO : suiteTokenDOList) {
            SuiteTokenVO suiteTokenVO = SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(suiteTokenDO);
            resultList.add(suiteTokenVO);

        }
        return ServiceResult.success(resultList);
    }

    @Override
    public ServiceResult<SuiteTokenVO> getSuiteToken(String suiteKey) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            SuiteTokenDO suiteTokenDO = suiteTokenDao.getSuiteTokenByKey(suiteKey);
            SuiteTokenVO suiteTokenVO =  SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(suiteTokenDO);
            return ServiceResult.success(suiteTokenVO);
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
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
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteTicketVO", JSON.toJSONString(suiteTicketVO))
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteTicketVO", JSON.toJSONString(suiteTicketVO))
            ),e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    @Override
    public ServiceResult<List<SuiteTicketVO>> getAllSuiteTicket() {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START
        ));
        try{
            List<SuiteTicketVO> resultList = new ArrayList<SuiteTicketVO>();
            List<SuiteTicketDO> suiteTicketDOList = suiteTicketDao.getAllSuiteTicket();
            if(CollectionUtils.isEmpty(suiteTicketDOList)){
                return ServiceResult.success(resultList);
            }
            for(SuiteTicketDO suiteTicketDO:suiteTicketDOList){
                SuiteTicketVO suiteTicketVO = SuiteTicketConvert.suiteTicketDO2SuiteTicketVO(suiteTicketDO);
                resultList.add(suiteTicketVO);
            }
            return ServiceResult.success(resultList);
        }catch (Exception e){
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"
            ));
            mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常"
            ));
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<SuiteTicketVO> getSuiteTicket(String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try{
            SuiteTicketDO suiteTicketDO = suiteTicketDao.getSuiteTicketByKey(suiteKey);
            SuiteTicketVO suiteTicketVO = SuiteTicketConvert.suiteTicketDO2SuiteTicketVO(suiteTicketDO);
            return ServiceResult.success(suiteTicketVO);
        }catch (Exception e){
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
