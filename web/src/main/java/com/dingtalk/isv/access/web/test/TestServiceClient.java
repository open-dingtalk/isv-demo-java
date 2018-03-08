package com.dingtalk.isv.access.web.test;

import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;


/**
 */
public class TestServiceClient {

    public static void main(String[] args) {
        String url = "http://120.55.87.34:9001/isv-crm-access/r/TestService";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            TestService testService = (TestService) factory.create(TestService.class, url);
            System.out.println(testService.hello("suiteqcempfnjclsel6rl"));
            //System.err.println(corpManageService.deleteCorpToken(suiteKey,corpId));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        /**
        url = "http://120.55.87.34:7001/r/CorpManageService";
        factory = new HessianProxyFactory();
        try {
            CorpManageService corpManageService = (CorpManageService) factory.create(CorpManageService.class, url);
            System.out.println(JSON.toJSONString(corpManageService.getCorpToken("aa","bb")));
            //System.err.println(corpManageService.deleteCorpToken(suiteKey,corpId));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        url = "http://127.0.0.1:8080/ding-isv-accesss/r/StaffManageService";
        factory = new HessianProxyFactory();
        try {
            EmpManageService staffManageService = (EmpManageService) factory.create(EmpManageService.class, url);
            System.out.println(JSON.toJSONString(staffManageService.getStaff("aa","bb","cc")));
            //System.err.println(corpManageService.deleteCorpToken(suiteKey,corpId));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
         **/




    }
}
