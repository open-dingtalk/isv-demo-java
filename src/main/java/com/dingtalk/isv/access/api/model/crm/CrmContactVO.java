package com.dingtalk.isv.access.api.model.crm;


import java.io.Serializable;
import java.util.List;

/**
 * @author qingshui.yf 10/12/15
 */
public class CrmContactVO implements Serializable {

    private static final long      serialVersionUID = 5882316013839545785L;

    /**
     * 联系人ID
     */
    private String                 contactId;

    /**
     * 客户id
     */
    private String                 customerId;

    /**
     * 分表字段
     */
    private String                   corpId;

    /**
     * 联系人名称
     */
    private String                 name;

    /**
     * 手机号码
     */
    private String                 mobile;

    /**
     * 手机号码国家码
     */
    private String                 stateCode;

    /**
     * 扩展信息json: key 属性id, value用户提供
     */
    private String                 attached;

    /**
     * 扩展信息格式化
     */
    private List<CrmExtPropertyVO> crmExtProList;

    /**
     * 客户信息
     */
    private CrmCustomerVO          customerVO;

    private String formData;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAttached() {
        return attached;
    }

    public void setAttached(String attached) {
        this.attached = attached;
    }

    public List<CrmExtPropertyVO> getCrmExtProList() {
        return crmExtProList;
    }

    public void setCrmExtProList(List<CrmExtPropertyVO> crmExtProList) {
        this.crmExtProList = crmExtProList;
    }

    public CrmCustomerVO getCustomerVO() {
        return customerVO;
    }

    public void setCustomerVO(CrmCustomerVO customerVO) {
        this.customerVO = customerVO;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }
}
