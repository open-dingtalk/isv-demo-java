package com.dingtalk.isv.access.api.model;

import java.util.List;

/**
 * 授权套件的企业信息
 * Created by mint on 16-8-30.
 */
public class CorpAuthInfoVO {
    /**
     * 企业基本信息
     */
    private CorpAuthInfoVO.AuthCorpInfo auth_corp_info;
    /**
     * 授权操作人信息
     */
    private CorpAuthInfoVO.AuthUserInfo auth_user_info;
    /**
     * 授权微应用相关信息(如果套件中有微应用,那么企业授权套件开通微应用,就会有这部分信息)
     */
    private CorpAuthInfoVO.AuthInfo auth_info;
    /**
     * 授权微应用相关信息(如果套件中有服务窗应用,那么企业授权套件开通服务窗应用,就会有这部分信息)
     */
    private CorpAuthInfoVO.ChannelAuthInfo channel_auth_info;
    public CorpAuthInfoVO() {
    }

    public CorpAuthInfoVO.AuthCorpInfo getAuth_corp_info() {
        return this.auth_corp_info;
    }

    public void setAuth_corp_info(CorpAuthInfoVO.AuthCorpInfo auth_corp_info) {
        this.auth_corp_info = auth_corp_info;
    }

    public CorpAuthInfoVO.AuthUserInfo getAuth_user_info() {
        return this.auth_user_info;
    }

    public void setAuth_user_info(CorpAuthInfoVO.AuthUserInfo auth_user_info) {
        this.auth_user_info = auth_user_info;
    }

    public CorpAuthInfoVO.AuthInfo getAuth_info() {
        return this.auth_info;
    }

    public void setAuth_info(CorpAuthInfoVO.AuthInfo auth_info) {
        this.auth_info = auth_info;
    }

    public ChannelAuthInfo getChannel_auth_info() {
        return channel_auth_info;
    }

    public void setChannel_auth_info(ChannelAuthInfo channel_auth_info) {
        this.channel_auth_info = channel_auth_info;
    }

    public static class AuthCorpInfo {
        private String corp_logo_url;
        private String corp_name;
        private String corpid;
        private String industry;
        private String invite_code;
        private String license_code;
        private String invite_url;

        public AuthCorpInfo() {
        }

        public String getCorp_logo_url() {
            return this.corp_logo_url;
        }

        public void setCorp_logo_url(String corp_logo_url) {
            this.corp_logo_url = corp_logo_url;
        }

        public String getCorp_name() {
            return this.corp_name;
        }

        public void setCorp_name(String corp_name) {
            this.corp_name = corp_name;
        }

        public String getCorpid() {
            return this.corpid;
        }

        public void setCorpid(String corpid) {
            this.corpid = corpid;
        }

        public String getIndustry() {
            return this.industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getInvite_code() {
            return this.invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getLicense_code() {
            return this.license_code;
        }

        public void setLicense_code(String license_code) {
            this.license_code = license_code;
        }

        public String getInvite_url() {
            return this.invite_url;
        }

        public void setInvite_url(String invite_url) {
            this.invite_url = invite_url;
        }
    }

    public static class AuthUserInfo {
        private String userId;

        public AuthUserInfo() {
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class AuthInfo {
        private List<CorpAuthInfoVO.Agent> agent;

        public AuthInfo() {
        }

        public List<CorpAuthInfoVO.Agent> getAgent() {
            return this.agent;
        }

        public void setAgent(List<CorpAuthInfoVO.Agent> agent) {
            this.agent = agent;
        }
    }

    public static class Agent {
        private String agent_name;
        private Long agentid;
        private Long appid;
        private String logo_url;

        public Agent() {
        }

        public String getAgent_name() {
            return this.agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public Long getAgentid() {
            return this.agentid;
        }

        public void setAgentid(Long agentid) {
            this.agentid = agentid;
        }

        public Long getAppid() {
            return this.appid;
        }

        public void setAppid(Long appid) {
            this.appid = appid;
        }

        public String getLogo_url() {
            return this.logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }
    }


    public static class ChannelAuthInfo {
        private List<CorpAuthInfoVO.ChannelAgent> channelAgent;

        public ChannelAuthInfo() {
        }

        public List<ChannelAgent> getChannelAgent() {
            return channelAgent;
        }

        public void setChannelAgent(List<ChannelAgent> channelAgent) {
            this.channelAgent = channelAgent;
        }
    }

    public static class ChannelAgent {
        private String agent_name;
        private Long agentid;
        private Long appid;
        private String logo_url;

        public ChannelAgent() {
        }

        public String getAgent_name() {
            return this.agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public Long getAgentid() {
            return this.agentid;
        }

        public void setAgentid(Long agentid) {
            this.agentid = agentid;
        }

        public Long getAppid() {
            return this.appid;
        }

        public void setAppid(Long appid) {
            this.appid = appid;
        }

        public String getLogo_url() {
            return this.logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }
    }








}
