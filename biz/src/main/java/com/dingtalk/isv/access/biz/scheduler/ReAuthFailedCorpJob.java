package com.dingtalk.isv.access.biz.scheduler;

import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.UnActiveCorpVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.dingutil.ISVRequestHelper;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ReAuthFailedCorpJob {
    private static final Logger mainLogger = LoggerFactory.getLogger(ReAuthFailedCorpJob.class);
    private static final Logger bizLogger = LoggerFactory.getLogger("TASK_LOG");
    @Resource
    private ISVRequestHelper isvRequestHelper;
    @Resource
    private SuiteManageService suiteManageService;

    /**
     * 套件key
     */
    private String suiteKey;

    /**
     * 应用ID
     */
    private String microappAppId;

    /**
     * 对开通套件微应用失败的授权企业重新开通。
     */
    public void reAuthFaileApp() {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                "任务开始。。。"
            ));

            ServiceResult<SuiteTokenVO> suiteTokenSr = suiteManageService.getSuiteToken(suiteKey);
            String suiteToken = suiteTokenSr.getResult().getSuiteToken();
            ServiceResult<List<UnActiveCorpVO>> corpListSr = isvRequestHelper.getUnactiveCorp(suiteToken,Long.valueOf(microappAppId));
            List<String> corpIdList = new ArrayList<String>();
            if(corpListSr.isSuccess() && !CollectionUtils.isEmpty(corpListSr.getResult())){
                for(UnActiveCorpVO unActiveCorpVO:corpListSr.getResult()){
                    corpIdList.add(unActiveCorpVO.getCorpId());
                }
            }
            if(!CollectionUtils.isEmpty(corpIdList)){
                isvRequestHelper.reAuthCorp(suiteToken,Long.valueOf(microappAppId),corpIdList);
            }
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                "任务结束。。。"
            ));
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                "程序异常"+e.toString()
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
        }
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getMicroappAppId() {
        return microappAppId;
    }

    public void setMicroappAppId(String microappAppId) {
        this.microappAppId = microappAppId;
    }
}
