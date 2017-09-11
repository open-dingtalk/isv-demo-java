package com.dingtalk.isv.access.biz.scheduler;

import com.dingtalk.isv.access.api.model.suite.SuiteTicketVO;
import com.dingtalk.isv.access.api.model.suite.SuiteTokenVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mint on 16-1-29.
 */
public class MonitorJob implements InitializingBean {
    private static final Logger    bizLogger = LoggerFactory.getLogger("MONITOR_LOG");
    @Autowired
    private SuiteManageService suiteManageService;
    private static Timer timer = new Timer();
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            timer.schedule(new TimerTask() {
                public void run() {
                    monitor();
                }
            }, 0, 1000 * 60 * 10);
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    "monitor task faile"
            ),e);
        }
    }


    private void monitor(){
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    "monitorticket任务开始"
            ));
            Long timeStamp = System.currentTimeMillis();
            ServiceResult<List<SuiteTicketVO>> ticketSr = suiteManageService.getAllSuiteTicket();
            if(!ticketSr.isSuccess() || CollectionUtils.isEmpty(ticketSr.getResult())){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        "monitor get ticket faile"
                ));
            }
            List<SuiteTicketVO> ticketList = ticketSr.getResult();
            Long checkTicketTime = 1000*60*20L;//ticket是最多20分钟推一次
            for(SuiteTicketVO vo:ticketList){
                Long diffTime = timeStamp-vo.getGmtModified().getTime();
                bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        "monitor ticket",
                        LogFormatter.KeyValue.getNew("suiteKey", vo.getSuiteKey()),
                        LogFormatter.KeyValue.getNew("modifiedTime", vo.getGmtModified()),
                        LogFormatter.KeyValue.getNew("diffTime", diffTime),
                        LogFormatter.KeyValue.getNew("checkTicketTime", checkTicketTime)
                ));
                if(diffTime>checkTicketTime){
                    bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                            "monitor ticket",
                            LogFormatter.KeyValue.getNew("suiteKey", vo.getSuiteKey()),
                            LogFormatter.KeyValue.getNew("modifiedTime", vo.getGmtModified()),
                            LogFormatter.KeyValue.getNew("diffTime", diffTime),
                            LogFormatter.KeyValue.getNew("checkTicketTime", checkTicketTime)
                    ));
                }
            }

            ServiceResult<List<SuiteTokenVO>> tokenSr = suiteManageService.getAllSuiteToken();
            if(!tokenSr.isSuccess() || CollectionUtils.isEmpty(tokenSr.getResult())){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        "monitor get token faile"
                ));
            }
            List<SuiteTokenVO> tokenList = tokenSr.getResult();
            Long checkTokenTime = 1000*60*30L;//token是30分钟轮询一次
            for(SuiteTokenVO vo:tokenList){
                Long diffTime = timeStamp-vo.getGmtModified().getTime();
                bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        "monitor token",
                        LogFormatter.KeyValue.getNew("suiteKey", vo.getSuiteKey()),
                        LogFormatter.KeyValue.getNew("modifiedTime", vo.getGmtModified()),
                        LogFormatter.KeyValue.getNew("diffTime", diffTime),
                        LogFormatter.KeyValue.getNew("checkTokenTime", checkTokenTime)
                ));
                if(diffTime>checkTokenTime){
                    bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                            "monitor token",
                            LogFormatter.KeyValue.getNew("suiteKey", vo.getSuiteKey()),
                            LogFormatter.KeyValue.getNew("modifiedTime", vo.getGmtModified()),
                            LogFormatter.KeyValue.getNew("diffTime", diffTime),
                            LogFormatter.KeyValue.getNew("checkTokenTime", checkTokenTime)
                    ));
                }
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "monitor error"+e.toString()
            ),e);
        }
    }
}
