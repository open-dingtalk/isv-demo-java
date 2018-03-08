package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 钉钉开放平台返回的部门详情对象
 * 对于ISV来说这是个基本对象。ISV可以根据自己的业务来继承这个对象
 */
public class DingDepartmentVO implements Serializable{
    private static final long serialVersionUID = 1L;
    //企业ID
    private String corpId;
    //部门ID
    private Long deptId;
    //部门排序值
    private Long order;
    //部门是否隐藏
    private Boolean deptHiding;
    //可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效
    private List<Long> deptPerimitList;
    //企业部门群的群主
    private String orgDeptOwner;
    //部门主管列表
    private List<String> deptManagerUserIdList;
    //可以查看指定隐藏部门的其他人员列表
    private List<String> userPerimitList;
    //是否部门下的员工仅自己可见。
    private Boolean outerDept;
    //部门群是否包含下级部门的人员
    private Boolean groupContainSubDept;
    //本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用| 符号进行分割
    private List<String> outerPermitUserList;
    //本部门的员工仅可见员工自己为true时，可以配置额外可见部门，
    private List<Long> outerPermitDeptList;
    //是否同步创建一个关联此部门的企业群
    private Boolean createDeptGroup;
    //部门名称
    private String name;
    //当群已经创建后，是否有新人加入部门会自动加入该群
    private Boolean autoAddUser;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Boolean getDeptHiding() {
        return deptHiding;
    }

    public void setDeptHiding(Boolean deptHiding) {
        this.deptHiding = deptHiding;
    }

    public List<Long> getDeptPerimitList() {
        return deptPerimitList;
    }

    public void setDeptPerimitList(List<Long> deptPerimitList) {
        this.deptPerimitList = deptPerimitList;
    }

    public String getOrgDeptOwner() {
        return orgDeptOwner;
    }

    public void setOrgDeptOwner(String orgDeptOwner) {
        this.orgDeptOwner = orgDeptOwner;
    }

    public List<String> getDeptManagerUserIdList() {
        return deptManagerUserIdList;
    }

    public void setDeptManagerUserIdList(List<String> deptManagerUserIdList) {
        this.deptManagerUserIdList = deptManagerUserIdList;
    }

    public List<String> getUserPerimitList() {
        return userPerimitList;
    }

    public void setUserPerimitList(List<String> userPerimitList) {
        this.userPerimitList = userPerimitList;
    }

    public Boolean getOuterDept() {
        return outerDept;
    }

    public void setOuterDept(Boolean outerDept) {
        this.outerDept = outerDept;
    }

    public Boolean getGroupContainSubDept() {
        return groupContainSubDept;
    }

    public void setGroupContainSubDept(Boolean groupContainSubDept) {
        this.groupContainSubDept = groupContainSubDept;
    }

    public List<String> getOuterPermitUserList() {
        return outerPermitUserList;
    }

    public void setOuterPermitUserList(List<String> outerPermitUserList) {
        this.outerPermitUserList = outerPermitUserList;
    }

    public List<Long> getOuterPermitDeptList() {
        return outerPermitDeptList;
    }

    public void setOuterPermitDeptList(List<Long> outerPermitDeptList) {
        this.outerPermitDeptList = outerPermitDeptList;
    }

    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAutoAddUser() {
        return autoAddUser;
    }

    public void setAutoAddUser(Boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
