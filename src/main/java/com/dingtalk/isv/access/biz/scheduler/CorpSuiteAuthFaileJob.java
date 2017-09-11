package com.dingtalk.isv.access.biz.scheduler;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.enums.suite.AuthFaileType;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.biz.suite.dao.CorpSuiteAuthFaileDao;
import com.dingtalk.isv.access.biz.suite.model.CorpSuiteAuthFaileDO;
import com.dingtalk.isv.common.log.format.LogFormatter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.List;

/**
 * 企业开通授权失败的异常重试任务
 * Created by lifeng.zlf on 2016/1/19.
 */
public class CorpSuiteAuthFaileJob extends QuartzJobBean {
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteTokenGenerateJob.class);
    private static final Logger    bizLogger = LoggerFactory.getLogger("TASK_LOGGER");
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
            ));
            try{
                XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
                CorpSuiteAuthFaileDao corpSuiteAuthFaileDao = (CorpSuiteAuthFaileDao) xmlWebApplicationContext.getBean("corpSuiteAuthFaileDao");
                CorpSuiteAuthService corpSuiteAuthService = (CorpSuiteAuthService) xmlWebApplicationContext.getBean("corpSuiteAuthService");

                List<CorpSuiteAuthFaileDO> list = corpSuiteAuthFaileDao.getCorpSuiteAuthFaileList(0,200);
                for(CorpSuiteAuthFaileDO corpSuiteAuthFaileDO:list){
                    bizLogger.info(LogFormatter.getKVLogData(null,
                            LogFormatter.KeyValue.getNew("faileObject", JSON.toJSONString(corpSuiteAuthFaileDO)),
                            LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
                    ));
                    corpSuiteAuthFaileDao.deleteById(corpSuiteAuthFaileDO.getId());

                    //如果是在激活为应用的阶段失败了.进行如下重试策略
                    if(AuthFaileType.ACTIVE_CORP_APP_FAILE.equals(corpSuiteAuthFaileDO.getAuthFaileType())){
                        corpSuiteAuthService.activeCorpApp(corpSuiteAuthFaileDO.getSuiteKey(),corpSuiteAuthFaileDO.getCorpId(),corpSuiteAuthFaileDO.getFaileInfo());
                    }
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
