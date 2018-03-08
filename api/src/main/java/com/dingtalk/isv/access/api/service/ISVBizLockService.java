package com.dingtalk.isv.access.api.service;

import com.dingtalk.isv.access.api.model.ISVBizLockVO;
import com.dingtalk.isv.access.common.model.ServiceResult;

import java.util.Date;

/**
 * 开放平台企业通讯录员工相关接口封装
 * Created by mint on 16-1-22.
 */
public interface ISVBizLockService{

    public ServiceResult<ISVBizLockVO> getISVBizLock(String lockKey,Date date);


    public ServiceResult<Void> removeISVBizLock(String lockKey);



}
