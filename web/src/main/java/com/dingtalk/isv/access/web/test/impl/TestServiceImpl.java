package com.dingtalk.isv.access.web.test.impl;

import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.web.test.TestService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class TestServiceImpl implements TestService {

    private Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    public String hello(String name) {
        logger.info("say hello to " + name);
        if (StringUtils.isBlank(name)) {
            return "Hello World!";
        } else {
            return "Hello " + name;
        }
    }


    @Override
    public ServiceResult<CorpTokenVO> hello1(String name, String name2){
        CorpTokenVO corpTokenVO = new CorpTokenVO();
        corpTokenVO.setCorpToken("cccccccccccc");
        corpTokenVO.setSuiteKey(name);
        corpTokenVO.setCorpId(name2);
        return ServiceResult.success(corpTokenVO);
    }

    @Override
    public ServiceResult<Integer> add(int a, int b) {
        return ServiceResult.success(a+b);
    }
}
