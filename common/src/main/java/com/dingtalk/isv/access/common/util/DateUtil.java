package com.dingtalk.isv.access.common.util;

import java.util.Date;

/**
 * Created by lifeng.zlf on 2016/3/20.
 */
public class DateUtil {

    public static String formatDate(Date date,String pattern){
        return (new java.text.SimpleDateFormat(pattern)).format(date);
    }

}
