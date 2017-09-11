package com.dingtalk.isv.access.biz.scheduler;

import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.List;

/**
 * 生成或更新suite访问开放平台接口token任务
 * Created by lifeng.zlf on 2016/1/19.
 */
public class SuiteTokenGenerateJob extends QuartzJobBean {
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteTokenGenerateJob.class);
    private static final Logger    bizLogger = LoggerFactory.getLogger("TASK_LOGGER");
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    "套件TOKEN生成任务执行开始",
                    LogFormatter.KeyValue.getNew("nextFireTime", jobExecutionContext.getNextFireTime())
            ));
            //获取所有套件信息
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            SuiteManageService suiteManageService = (SuiteManageService)xmlWebApplicationContext.getBean("suiteManageService");
            ServiceResult<List<SuiteVO>> sr = suiteManageService.getAllSuite();
            if(!sr.isSuccess() || CollectionUtils.isEmpty(sr.getResult())){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "查询套件信息失败",
                        sr.getCode(),
                        sr.getMessage()
                ));
                return;
            }
            List<SuiteVO> suiteList = sr.getResult();
            //分别更换套件token
            for(SuiteVO suiteVO:suiteList){
                ServiceResult<Void> tokenSr = suiteManageService.saveOrUpdateSuiteToken(suiteVO.getSuiteKey());
                if(!tokenSr.isSuccess()){
                    bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "生成token任务失败",
                            tokenSr.getCode(),
                            tokenSr.getMessage(),
                            LogFormatter.KeyValue.getNew("suiteKey", suiteVO.getSuiteKey()),
                            LogFormatter.KeyValue.getNew("suiteName", suiteVO.getSuiteName())
                    ));
                }
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "任务执行异常"+e.toString()
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "任务执行异常"+e.toString()
            ),e);
        }
    }
}

