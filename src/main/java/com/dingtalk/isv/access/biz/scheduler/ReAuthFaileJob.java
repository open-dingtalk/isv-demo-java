package com.dingtalk.isv.access.biz.scheduler;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.enums.suite.AuthFaileType;
import com.dingtalk.isv.access.api.model.suite.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.model.suite.UnActiveCorpVO;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.dingutil.ISVRequestHelper;
import com.dingtalk.isv.access.biz.suite.dao.AppDao;
import com.dingtalk.isv.access.biz.suite.dao.CorpSuiteAuthFaileDao;
import com.dingtalk.isv.access.biz.suite.model.AppDO;
import com.dingtalk.isv.access.biz.suite.model.CorpSuiteAuthFaileDO;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 当企业授权套件开通微应用的时候,有可能因为各种问题导致没有授权成功,而ISV
 * 并不知道企业有过授权行为。 这一点注意和CorpSuiteAuthFaileJob区别开.
 * {CorpSuiteAuthFaileJob}是知道了企业有授权行为,至少受到过临时授权码
 *
 * 那么需要重新补偿开通一下
 *
 * Created by lifeng.zlf on 2016/1/19.
 */
public class ReAuthFaileJob extends QuartzJobBean {
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteTokenGenerateJob.class);
    private static final Logger    bizLogger = LoggerFactory.getLogger("TASK_LOGGER");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
            ));
            try{
                XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
                SuiteManageService suiteManageService = (SuiteManageService)xmlWebApplicationContext.getBean("suiteManageService");
                ISVRequestHelper isvRequestHelper = (ISVRequestHelper)xmlWebApplicationContext.getBean("isvRequestHelper");
                JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
                String suiteKey = data.getString("suiteKey");
                Long appId = data.getLong("appId");
                ServiceResult<SuiteTokenVO> suiteTokenSr = suiteManageService.getSuiteToken(suiteKey);
                if(!suiteTokenSr.isSuccess() || null==suiteTokenSr.getResult()){
                    bizLogger.warn(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "suiteToken获取失败",
                            suiteTokenSr.getCode(),
                            suiteTokenSr.getMessage(),
                            LogFormatter.KeyValue.getNew("data", JSON.toJSONString(data)),
                            LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
                    ));
                    return;
                }

                ServiceResult<List<UnActiveCorpVO>> unActiveSr =  isvRequestHelper.getUnactiveCorp(suiteTokenSr.getResult().getSuiteToken(),appId);
                if(!unActiveSr.isSuccess()){
                    bizLogger.warn(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "获取未激活企业列表失败",
                            unActiveSr.getCode(),
                            unActiveSr.getMessage(),
                            LogFormatter.KeyValue.getNew("data", JSON.toJSONString(data)),
                            LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
                    ));
                    return;
                }

                List<String> corpIdList = new ArrayList<String>();
                for(UnActiveCorpVO unActiveCorpVO:unActiveSr.getResult()){
                    corpIdList.add(unActiveCorpVO.getCorpId());
                }

                if(!CollectionUtils.isEmpty(corpIdList)){
                    isvRequestHelper.reAuthCorp(suiteTokenSr.getResult().getSuiteToken(),appId,corpIdList);
                }

            }catch (Exception e){
                bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
                ),e);
                mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
                ),e);
            }
    }
}
