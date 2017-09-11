package com.dingtalk.isv.access.api.model.microapp.report;

import java.io.Serializable;

/**
 * 日志模板
 * Created by lifeng.zlf on 2016/5/26.
 */
public class ReportTemplate implements Serializable {
    private String templateId;
    private String templateName;
    private String url;
    private String icon;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
