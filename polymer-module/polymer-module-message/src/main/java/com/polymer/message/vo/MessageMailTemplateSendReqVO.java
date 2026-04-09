package com.polymer.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "管理后台 - 邮件发送 Req VO")
public class MessageMailTemplateSendReqVO {

    @Schema(description = "接收邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "7685413@qq.com")
    @NotEmpty(message = "接收邮箱不能为空")
    private String mail;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "模板编码不能为空")
    private String templateCode;

    @Schema(description = "模板参数")
    private Map<String, Object> templateParams;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, Object> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, Object> templateParams) {
        this.templateParams = templateParams;
    }
}
