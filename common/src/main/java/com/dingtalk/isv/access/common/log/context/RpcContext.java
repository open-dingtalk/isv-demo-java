package com.dingtalk.isv.access.common.log.context;

import java.util.UUID;

/**
 * Created by lifeng.zlf on 2016/4/11.
 */
public class RpcContext {
    public static ThreadLocal<String> traceId = new ThreadLocal<String>(){
        @Override
        public String get() {
            if(null ==  super.get()){
                super.set(UUID.randomUUID().toString());
            }
            return super.get();
        }
    };
}
