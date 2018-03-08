package com.dingtalk.isv.access.api.enums.suite;

/**
 * 套件相关回调枚举
 * Created by 浩倡 on 16-1-17.
 */
public enum  SuitePushType {
    /**校验url**/
    CHECK_URL("check_url"),
    /**临时授权码**/
    CHANGE_AUTH("change_auth"),
    /**套件ticket**/
    SUITE_TICKET("suite_ticket"),
    /**临时授权码**/
    TMP_AUTH_CODE("tmp_auth_code"),
    /**套件ticket**/
    SUITE_RELIEVE("suite_relieve"),
    /**校验创建套件时候的url**/
    CHECK_CREATE_SUITE_URL("check_create_suite_url"),
    /**校验更改套件时候的url**/
    CHECK_UPDATE_SUITE_URL("check_update_suite_url"),
    /**校验徐陶剑序列号**/
    CHECK_SUITE_LICENSE_CODE("check_suite_license_code");
    private final String key;

    SuitePushType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static SuitePushType getISVPushEventType(String key){
        SuitePushType[] suitePushTypeArr = SuitePushType.values();
        for (SuitePushType o : suitePushTypeArr) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }

}