package com.dingtalk.isv.access.api.service.crm;

import com.dingtalk.isv.access.api.model.crm.CrmContactVO;
import com.dingtalk.isv.access.api.model.crm.CrmCustomerVO;
import com.dingtalk.isv.common.model.ResultWrapper;
import com.dingtalk.isv.common.model.ServiceResult;

import java.util.List;

/**
 * crm业务相关
 * Created by lifeng.zlf on 2016/3/1.
 */
public interface CrmManageService {

    /**
     * 获取客户信息
     * @param suiteKey
     * @param corpId
     * @param customerId
     * @return
     */
    public ServiceResult<CrmCustomerVO> getCrmCustomer(String suiteKey, String corpId, String customerId);

    /**
     * 删除客户信息
     * @param suiteKey
     * @param corpId
     * @param customerId
     * @return
     */
    public ServiceResult<Void> removeCrmCustomer(String suiteKey,String corpId, String customerId);

    /**
     *
     * @param suiteKey
     * @param corpId
     * @param customerId
     * @return
     */
    public ServiceResult<List<CrmContactVO>> getCrmContactList(String suiteKey,String corpId, String customerId,Integer offset,Integer lomit);


    /**
     * 查询客户联系人信息
     * @param suiteKey
     * @param corpId
     * @param customerId
     * @param contractId
     * @return
     */
    public ServiceResult<CrmContactVO> getCrmContact(String suiteKey,String corpId, String customerId,String contractId);

    /**
     * 绑定客户跟进人
     * @return
     */
    public ServiceResult<Void> bindCustom(String suiteKey,String corpId, String customerId,String statffId);

    /**
     * 暂未开放
     * @param suiteKey
     * @param corpId
     * @param start
     * @param offset
     * @return
     */
    @Deprecated
    public ServiceResult<ResultWrapper<String>> getCustomIdList(String suiteKey, String corpId, Integer start, Integer offset);

    /**
     * 更新客户最后跟进时间
     * @param suiteKey
     * @param corpId
     * @param customerId
     * @param timeStamp
     * @return
     */
    public ServiceResult<Void> updateFollowTime(String suiteKey, String corpId,String customerId,Long timeStamp);

    /**
     *
     * @param suiteKey
     * @param corpId
     * @return
     */
    public ServiceResult<Void> getCustomerBaseForm(String suiteKey, String corpId);


}
