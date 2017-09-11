package com.dingtalk.isv.access.biz.corp.service.impl;

import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.access.api.model.corp.DepartmentVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.corp.DeptManageService;

import com.dingtalk.isv.access.biz.corp.model.helper.DepartmentHelper;
import com.dingtalk.isv.common.code.ServiceResultCode;
import com.dingtalk.isv.common.log.format.LogFormatter;
import com.dingtalk.isv.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;
import com.dingtalk.open.client.api.service.corp.CorpDepartmentService;
import com.dingtalk.open.client.common.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by nanlai on 2016/1/26.
 */
public class DeptManageServiceImpl implements DeptManageService {
    private static final Logger bizLogger = LoggerFactory.getLogger("CORP_MANAGE_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(CorpManageServiceImpl.class);
    @Autowired
    private CorpDepartmentService corpDepartmentService;
    @Autowired
    private CorpManageService corpManageService;

    @Override
    public ServiceResult<DepartmentVO> getDept(Long deptId, String corpId, String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("deptId", deptId),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try {
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            DepartmentDetail departmentDetail = corpDepartmentService.getDeptDetail(corpTokenSr.getResult().getCorpToken(), deptId.toString());
            DepartmentVO departmentVO = DepartmentHelper.DepartmentDetail2DepartmentVO(departmentDetail);
            return ServiceResult.success(departmentVO);
        } catch (ServiceException e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "查询部门失败",
                    String.valueOf(e.getCode()),
                    e.getMessage(),
                    LogFormatter.KeyValue.getNew("deptId", deptId),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("deptId", deptId),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("deptId", deptId),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
