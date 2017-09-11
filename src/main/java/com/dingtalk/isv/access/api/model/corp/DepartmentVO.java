package com.dingtalk.isv.access.api.model.corp;

import java.io.Serializable;

/**
 * 部门对象
 * Created by mint on 16-7-17.
 */
public class DepartmentVO implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long order;
    private Boolean deptHiding;
    private String deptPerimits;
    private String orgDeptOwner;
    private String deptManagerUseridList;

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

    public String getDeptPerimits() {
        return deptPerimits;
    }

    public void setDeptPerimits(String deptPerimits) {
        this.deptPerimits = deptPerimits;
    }

    public String getOrgDeptOwner() {
        return orgDeptOwner;
    }

    public void setOrgDeptOwner(String orgDeptOwner) {
        this.orgDeptOwner = orgDeptOwner;
    }

    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList;
    }
}
