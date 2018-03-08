package com.dingtalk.isv.access.common.code;

/**
 * 全局错误码
 * 如果业务方有自己的业务错误码,可以重新定义
 * Created by lifeng.zlf on 2016/3/4.
 */
public enum ServiceResultCode {
    /**获取套件suiteToken失败**/
    SUCCESS("0","success"),
    SYS_ERROR("-1","系统繁忙"),
    BIZ_LOCK_EXIST("-10","锁存在"),
    BIZ_LOCK_NOT_EXIST("-11","锁不存在"),


    /**1000~2000定义为crm接入服务错误**/
    CORP_NOT_AUTH_SUITE("1000","企业未对套件授权"),
    SUITE_TICKET_NOT_FOUNT("1002","未查询到套件ticket"),
    SUITE_TOKEN_NOT_FOUNT("1003","未查询到套件token"),
    CORP_TOKEN_NOT_FOUNT("1004","未查询到企业token"),
    SUITE_NOT_FIND("1004","套件未查询到"),
    CORP_SUITE_CALLBACK_EXIST("1005","企业回调地址已经存在"),


    /**2000~3000定义为crm具体业务错误**/
    NOT_LOGIN ("2001","未登录"),
    PARAMETER_INVALID("2002","参数非法"),
    CUSTOM_NOT_FIND("2003","客户不存在"),
    CUSTOM_FIND_ERROR("2004","客户查询失败"),
    CONTACT_FIND_ERROR("2005","客户联系人查询失败"),
    CUSTOM_REMOVE_ERROR("2006","客户删除失败"),
    CUSTOM_FOLLOW_BIND_ERROR("2007","客户跟进人绑定失败"),
    NOT_ALLOW_TO_CUSTON("2008","无权限查看客户信息"),
    NOT_ALLOW_CUSTOM_FOLLOW_BIND("2009","无权限更换跟进人"),
    NOT_ALLOW_CUSTOM_REMOVE("2010","无权限删除客户"),
    LOGIN_FAILE ("2011","登录失败"),
    SAVE_CORP_FORM_DATA_FAILE ("2012","保存表单数据失败"),
    CORP_FORM_SCHEMA_FIND_FAILE ("2013","企业表单Schema查询失败"),
    CORP_FORM_DATA_SAVE_FAILE ("2014","数据保存失败"),
    IS_NOT_ADMIN ("2015","用户不是管理员"),
    IS_NOT_SUPER_ADMIN ("2017","用户不是主管理员"),
            ;


    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private ServiceResultCode(String errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
