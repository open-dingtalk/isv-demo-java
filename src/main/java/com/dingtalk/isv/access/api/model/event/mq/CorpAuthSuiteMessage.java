package com.dingtalk.isv.access.api.model.event.mq;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 套件回调事件
 * Created by mint on 16-7-17.
 */
public class CorpAuthSuiteMessage implements MessageCreator {

    public static enum Tag {
        Auth,           //授权
        RelieveAuth     //解授权
    }

    private String corpId;
    private String suiteKey;
    private Tag tag;

    public CorpAuthSuiteMessage(String corpId, String suiteKey, Tag tag) {
        this.corpId = corpId;
        this.suiteKey = suiteKey;
        this.tag = tag;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        message.setString("corpId", this.getCorpId());
        message.setString("suiteKey", this.getSuiteKey());
        message.setString("tag", this.getTag().toString());
        return message;
    }


    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }
}
