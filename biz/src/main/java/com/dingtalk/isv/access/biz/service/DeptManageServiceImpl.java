package com.dingtalk.isv.access.biz.service;

import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.model.DingDepartmentVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.DeptManageService;

import com.dingtalk.isv.access.biz.model.converter.DepartmentHelper;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;
import com.dingtalk.open.client.api.service.corp.CorpDepartmentService;
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
    public ServiceResult<DingDepartmentVO> getDept(Long deptId, String corpId, String suiteKey) {
        return null;
//        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
//                LogFormatter.KeyValue.getNew("deptId", deptId),
//                LogFormatter.KeyValue.getNew("corpId", corpId),
//                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
//        ));
//        try {
//            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
//            DepartmentDetail departmentDetail = corpDepartmentService.getDeptDetail(corpTokenSr.getResult().getCorpToken(), deptId.toString());
//            DingDepartmentVO departmentVO = DepartmentHelper.DepartmentDetail2DepartmentVO(departmentDetail);
//            return ServiceResult.success(departmentVO);
//        } catch (Exception e) {
//            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
//                    "系统异常" + e.toString(),
//                    LogFormatter.KeyValue.getNew("deptId", deptId),
//                    LogFormatter.KeyValue.getNew("corpId", corpId),
//                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
//            );
//            bizLogger.error(errLog, e);
//            mainLogger.error(errLog, e);
//            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
//        }
    }

    @Override
    public ServiceResult<DingDepartmentVO> saveDept(String suiteKey, String corpId, Long deptId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("deptId", deptId),
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try {
            ServiceResult<CorpTokenVO> corpTokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            DepartmentDetail departmentDetail = corpDepartmentService.getDeptDetail(corpTokenSr.getResult().getCorpToken(), deptId.toString());
            DingDepartmentVO departmentVO = DepartmentHelper.DepartmentDetail2DepartmentVO(departmentDetail);
            return ServiceResult.success(departmentVO);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常" + e.toString(),
                    LogFormatter.KeyValue.getNew("deptId", deptId),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
