package com.dingtalk.isv.access.web.test;

import com.dingtalk.isv.access.api.model.corp.CorpTokenVO;
import com.dingtalk.isv.common.model.ServiceResult;

/**
 */
public interface TestService {

    String hello(String name);


    ServiceResult<CorpTokenVO> hello1(String name, String name2);

    ServiceResult<Integer> add(int a,int b);
}
