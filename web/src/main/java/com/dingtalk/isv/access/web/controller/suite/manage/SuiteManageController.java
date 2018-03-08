package com.dingtalk.isv.access.web.controller.suite.manage;

import com.dingtalk.isv.access.api.model.SuiteVO;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 套件管理
 */
@Controller
public class SuiteManageController{
    private static final Logger     bizLogger = LoggerFactory.getLogger("SUITE_MANAGE_LOGGER");
    private static final Logger    mainLogger = LoggerFactory.getLogger(SuiteManageController.class);

    @Autowired
    private SuiteManageService suiteManageService;

    @ResponseBody
    @RequestMapping(value = "/suite/xxx", method = {RequestMethod.GET })
    public String addSuite() {
        String token="xxxxxx";
        String suiteKey="suitexxxxxxxxx";
        String aesKey="xxxxxxxxxxxxxxxxxxxxx";
        String secret="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        SuiteVO suiteVO = new SuiteVO();
        suiteVO.setEncodingAesKey(aesKey);
        suiteVO.setToken(token);
        suiteVO.setEventReceiveUrl("");
        suiteVO.setSuiteKey(suiteKey);
        suiteVO.setSuiteName("xxxxxxxxxxxxxxxx套件");
        suiteVO.setSuiteSecret(secret);
        suiteManageService.addSuite(suiteVO);
        bizLogger.info("this is a MonitorJob");

        return "success";
    }
}
