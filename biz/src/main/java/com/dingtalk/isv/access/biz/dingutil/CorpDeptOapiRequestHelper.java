package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.DingDepartmentVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 开放平台企业和部门相关的通讯录接口封装
 */
public class CorpDeptOapiRequestHelper {
    private static Logger mainLogger = LoggerFactory.getLogger(CorpDeptOapiRequestHelper.class);
    private static final Logger bizLogger = LoggerFactory.getLogger("HTTP_INVOKE_LOGGER");
    @Resource
    private HttpRequestHelper httpRequestHelper;
    private String oapiDomain;

    public String getOapiDomain() {
        return oapiDomain;
    }

    public void setOapiDomain(String oapiDomain) {
        this.oapiDomain = oapiDomain;
    }


    /**
     * 获取企业管理员
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param parentDeptId  父部门ID
     */
    public ServiceResult<List<Long>> getSubDeptIdList(String suiteKey, String corpId, String accessToken,Long parentDeptId) {
        try {
            String url = oapiDomain + "/department/list_ids?access_token=" + accessToken+"&id="+parentDeptId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                List<Long> subDeptIdList = new ArrayList<Long>();
                JSONArray jsonArray = jsonObject.getJSONArray("sub_dept_id_list");
                if(!CollectionUtils.isEmpty(jsonArray)){
                    subDeptIdList = JSONArray.parseArray(jsonArray.toJSONString(),Long.class);
                }
                return ServiceResult.success(subDeptIdList);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 直接访问钉钉开放平台获取部门详情
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业CorpId
     * @param accessToken   授权企业访问AccessToken
     * @param deptId        部门ID
     */
    public ServiceResult<DingDepartmentVO> getDeptById(String suiteKey, String corpId, String accessToken, Long deptId) {
        try {
            String url = oapiDomain + "/department/get?access_token=" + accessToken+"&id="+deptId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                DingDepartmentVO departmentVO = new DingDepartmentVO();
                List<String> userPerimitList = new ArrayList<String>();
                String userPerimits = jsonObject.getString("userPerimits");
                if(!StringUtils.isEmpty(userPerimits)){
                    userPerimitList = Arrays.asList(userPerimits.split("|"));
                }
                String deptManagerUserid = jsonObject.getString("deptManagerUseridList");
                List<String> deptManagerUserIdList = new ArrayList<String>();
                if(!StringUtils.isEmpty(deptManagerUserid)){
                    deptManagerUserIdList = Arrays.asList(deptManagerUserid.split("|"));
                }
                String outerPermitUsers = jsonObject.getString("outerPermitUsers");
                List<String> outerPermitUserList = new ArrayList<String>();
                if(!StringUtils.isEmpty(outerPermitUsers)){
                    outerPermitUserList = Arrays.asList(outerPermitUsers.split("|"));
                }
                String outerPermitDepts = jsonObject.getString("outerPermitDepts");
                List<Long> outerPermitDeptList = new ArrayList<Long>();
                if(!StringUtils.isEmpty(outerPermitDepts)){
                    for(String idstr:outerPermitDepts.split("|")){
                        outerPermitDeptList.add(Long.valueOf(idstr));
                    }
                }
                String deptPerimits = jsonObject.getString("deptPerimits");
                List<Long> deptPerimitList = new ArrayList<Long>();
                if(!StringUtils.isEmpty(deptPerimits)){
                    for(String idstr:deptPerimits.split("|")){
                        deptPerimitList.add(Long.valueOf(idstr));
                    }
                }
                departmentVO.setDeptManagerUserIdList(deptManagerUserIdList);
                departmentVO.setUserPerimitList(userPerimitList);
                departmentVO.setOrgDeptOwner(jsonObject.getString("orgDeptOwner"));
                departmentVO.setOuterDept(jsonObject.getBoolean("outerDept"));
                departmentVO.setOuterPermitUserList(outerPermitUserList);
                departmentVO.setOuterPermitDeptList(outerPermitDeptList);
                departmentVO.setDeptPerimitList(deptPerimitList);
                departmentVO.setCreateDeptGroup(jsonObject.getBoolean("createDeptGroup"));
                departmentVO.setName(jsonObject.getString("name"));
                departmentVO.setDeptId(jsonObject.getLong("id"));
                departmentVO.setAutoAddUser(jsonObject.getBoolean("autoAddUser"));
                departmentVO.setDeptHiding(jsonObject.getBoolean("deptHiding"));
                departmentVO.setOrder(jsonObject.getLong("order"));
                departmentVO.setCorpId(corpId);
                return ServiceResult.success(departmentVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }






}


