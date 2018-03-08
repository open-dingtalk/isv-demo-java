package com.dingtalk.isv.access.api.enums.suite;

/**
 * 企业授权套件失败场景枚举
 * Created by 浩倡 on 16-1-17.
 */
public enum AuthFaileType {

    /**获取套件suiteToken失败**/
    GET_SUITE_TOKEN_FAILE("get_suite_token_faile"),
    /**获取永久授权码失败**/
    GET_PERMANENT_CODE_FAILE("get_permanent_code_faile"),
    /**激活企业为应用失败**/
    ACTIVE_CORP_APP_FAILE("active_corp_app_faile"),
    /**获取企业信息失败**/
    GET_CORP_INFO_FAILE("get_corp_info_faile"),
    /**注册企业回调地址失败**/
    SAVE_CORP_SUITE_CALLBACK_FAILE("save_corp_suite_callback__faile");

    private final String key;

    private AuthFaileType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static AuthFaileType getAuthFaileType(String key){
        AuthFaileType[] authFaileTypeArr = AuthFaileType.values();
        for (AuthFaileType o : authFaileTypeArr) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }
}