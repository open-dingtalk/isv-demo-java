package com.dingtalk.isv.access.api.enums.suite;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权企业内变更事件枚举
 */
public enum CorpCallBackTypeEnum {
    USER_ADD_ORG("user_add_org"),

    USER_MODIFY_ORG("user_modify_org"),

    USER_LEAVE_ORG("user_leave_org"),

    ORG_ADMIN_ADD("org_admin_add"),

    ORG_ADMIN_REMOVE("org_admin_remove"),

    ORG_DEPT_CREATE("org_dept_create"),

    ORG_DEPT_MODIFY("org_dept_modify"),

    ORG_DEPT_REMOVE("org_dept_remove"),

    ORG_REMOVE("org_remove");

    private final String key;

    CorpCallBackTypeEnum (String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public static List<String> getAllTag(){
        List<String> tagList = new ArrayList<String>();
        CorpCallBackTypeEnum[] tagArr = CorpCallBackTypeEnum.values();
        for (CorpCallBackTypeEnum tag : tagArr) {
            tagList.add(tag.getKey());
        }
        return tagList;
    }
}