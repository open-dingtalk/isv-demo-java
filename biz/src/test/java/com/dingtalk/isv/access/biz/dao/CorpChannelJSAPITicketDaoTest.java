package com.dingtalk.isv.access.biz.dao;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.model.CorpChannelJSAPITicketDO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by mint on 16-8-29.
 */
public class CorpChannelJSAPITicketDaoTest extends BaseTestCase {
    @Resource
    private CorpChannelJSAPITicketDao corpChannelJSAPITicketDao;



    @Test
    public void test_saveOrUpdateCorpChannelJSAPITicket() {
        String corpId="ding423423423";
        String suiteKey="suiteqcempfnjclsel6rl";

        CorpChannelJSAPITicketDO corpChannelJSAPITicketDO = new CorpChannelJSAPITicketDO();
        corpChannelJSAPITicketDO.setSuiteKey(suiteKey);
        corpChannelJSAPITicketDO.setCorpId(corpId);
        corpChannelJSAPITicketDO.setCorpChannelJSAPITicket("123123");
        corpChannelJSAPITicketDO.setExpiredTime(new Date());
        corpChannelJSAPITicketDao.saveOrUpdateCorpChannelJSAPITicket(corpChannelJSAPITicketDO);
    }


    @Test
    public void test_getCorpChannelJSAPITicket() {
        String corpId="ding423423423";
        String suiteKey="suiteqcempfnjclsel6rl";
        CorpChannelJSAPITicketDO o = corpChannelJSAPITicketDao.getCorpChannelJSAPITicket(suiteKey,corpId);
        System.err.println(JSON.toJSONString(o));

    }


}
