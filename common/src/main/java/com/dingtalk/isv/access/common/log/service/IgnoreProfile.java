package com.dingtalk.isv.access.common.log.service;

import java.lang.annotation.*;

/**
 * Created by lifeng.zlf on 2016/1/25.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreProfile {
}
