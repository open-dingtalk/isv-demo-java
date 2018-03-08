package com.dingtalk.isv.access.biz.service;

import com.dingtalk.isv.access.api.model.ISVBizLockVO;
import com.dingtalk.isv.access.api.service.ISVBizLockService;
import com.dingtalk.isv.access.biz.dao.ISVBizLockDAO;
import com.dingtalk.isv.access.biz.model.ISVBizLockDO;
import com.dingtalk.isv.access.biz.model.converter.ISVBizLockConverter;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 开放平台企业通讯录员工相关接口封装
 * Created by mint on 16-1-22.
 */
public class ISVBizLockServiceImpl implements ISVBizLockService {
    private static final Logger mainLogger = LoggerFactory.getLogger(ISVBizLockServiceImpl.class);
    private static final Logger bizLogger = LoggerFactory.getLogger(ISVBizLockServiceImpl.class);
    @Resource
    ISVBizLockDAO isvBizLockDAO;

    public ServiceResult<ISVBizLockVO> getISVBizLock(String lockKey,Date date){
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("lockKey", lockKey),
                LogFormatter.KeyValue.getNew("date", date)
        ));
        try{
            isvBizLockDAO.saveISVBizLock(lockKey,date);
            ISVBizLockDO isvBizLockDO = isvBizLockDAO.getISVBizLockByLockKey(lockKey);
            return ServiceResult.success(ISVBizLockConverter.isvBizLockDO2ISVBizLockVO(isvBizLockDO));
        }catch (Exception e){
            ISVBizLockDO isvBizLockDO = isvBizLockDAO.getISVBizLockByLockKey(lockKey);
            if(null!=isvBizLockDO&&new Date().compareTo(isvBizLockDO.getExpire())>0){
                //兼容一下异常逻辑,如果锁过期了没有删掉,删除一下
                isvBizLockDAO.removeISVBizLock(lockKey);
            }
            return ServiceResult.failure("-1","获取锁失败");
        }
    }


    public ServiceResult<Void> removeISVBizLock(String lockKey){
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("lockKey", lockKey)
        ));
        isvBizLockDAO.removeISVBizLock(lockKey);
        return ServiceResult.success(null);
    }

}
