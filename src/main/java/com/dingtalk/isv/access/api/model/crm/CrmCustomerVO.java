package com.dingtalk.isv.access.api.model.crm;


import java.io.Serializable;
import java.util.List;

/**
 *客户信息
 */
public class CrmCustomerVO implements Serializable {
    private static final long serialVersionUID = -3114755210991928302L;
    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 分表字段
     */
    private String corpId;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 扩展字段json
     */
    private String attached;
    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String description;

    /**
     * 电话
     */
    private String phone;

    /**
     * 人数
     */
    private Integer memberCount;
    /**
     * 扩展
     */
    private String formData;
    /**
     * 跟进人
     */
    private List<String> followStaffIds;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getAttached() {
        return attached;
    }

    public void setAttached(String attached) {
        this.attached = attached;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public List<String> getFollowStaffIds() {
        return followStaffIds;
    }

    public void setFollowStaffIds(List<String> followStaffIds) {
        this.followStaffIds = followStaffIds;
    }
}
