package com.dingtalk.isv.access.biz;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.MessageBody;

import java.util.Arrays;

/**
 * Created by lifeng.zlf on 2016/4/11.
 */
public class TestMain {
    public static void main(String [] args){
        Long start = System.currentTimeMillis();
        String suiteKey="suiteytzpzchcpug3xpsm";
        String corpId = "ding4ed6d279061db5e7";
        Long appId = -23L;
        MessageBody.OABody message = new MessageBody.OABody();
        MessageBody.OABody.Head head = new MessageBody.OABody.Head();
        MessageBody.OABody.Body body = new MessageBody.OABody.Body();
        head.setText("HEAD");
        head.setBgcolor("ffffa328");
        head.setText("客户详情");
        body.setContent("标题 \r\n 换行");
        body.setTitle("xxxxxxxx \r\n  换行");
        message.setHead(head);
        message.setBody(body);
        message.setMessage_url("http://qr.dingtalk.com/page/crminfo?appid=-23&corpid=%24"+corpId+"%24");
        System.err.println(JSON.toJSONString(message));
    }
}
