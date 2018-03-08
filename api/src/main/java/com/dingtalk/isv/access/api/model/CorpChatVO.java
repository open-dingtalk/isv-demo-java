package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 企业群VO。和钉钉开放平台chat系列接口对应
 */
public class CorpChatVO implements Serializable {
    /**
     * 授权企业CorpId
     */
    private String corpId;
    /**
     * 群ID
     */
    private String chatId;
    /**
     * 群名称
     */
    private String chatName;
    /**
     * 群主UserId
     */
    private String ownerUserId;
    /**
     * 群成员UserId列表
     */
    private List<String> userIdlist;
    /**
     * 外部联系人UserId列表
     */
    private List<String> extIdList;
    /**
     * 群类型 0:普通群   2:企业群
     * 普通群可以加非企业人员,外部联系人可以加  企业群只能加企业员工,外部联系人不能加
     */
    private Long conversationTag;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public List<String> getUserIdlist() {
        return userIdlist;
    }

    public void setUserIdlist(List<String> userIdlist) {
        this.userIdlist = userIdlist;
    }

    public List<String> getExtIdList() {
        return extIdList;
    }

    public void setExtIdList(List<String> extIdList) {
        this.extIdList = extIdList;
    }

    public Long getConversationTag() {
        return conversationTag;
    }

    public void setConversationTag(Long conversationTag) {
        this.conversationTag = conversationTag;
    }
}
