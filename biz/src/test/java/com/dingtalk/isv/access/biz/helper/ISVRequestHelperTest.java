package com.dingtalk.isv.access.biz.helper;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.ISVSSOTokenVO;
import com.dingtalk.isv.access.api.model.OALoginUserVO;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.ISVRequestHelper;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lifeng.zlf on 2017/12/1.
 */
public class ISVRequestHelperTest extends BaseTestCase {
    @Resource
    private ISVRequestHelper isvRequestHelper;
    @Resource
    private CorpOapiRequestHelper corpOapiRequestHelper;
    @Test
    public void test_getSSOToken(){
        String corpId = "ding9f50b15bccd16741";
        String corpSecret = "";
        ServiceResult<ISVSSOTokenVO> tokenSr =  isvRequestHelper.getSSOToken(corpId,corpSecret);
        //code=de66252e02133fac8eb04f4a236f0e5c
        ServiceResult<OALoginUserVO> sr = corpOapiRequestHelper.getEmpBySSOCode(null,tokenSr.getResult().getIsvSsoToken(),"dbd854a89bbc398694f30232633be815");
        System.err.println(JSON.toJSONString(sr));


    }
}
