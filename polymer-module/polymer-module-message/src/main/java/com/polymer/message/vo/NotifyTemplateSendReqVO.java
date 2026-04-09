package com.polymer.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 站内信模板的发送 Request VO")
public class NotifyTemplateSendReqVO {

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户id不能为空")
    private List<Long> userIds;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    @NotEmpty(message = "模板编码不能为空")
    private String templateCode;

    @Schema(description = "模板参数")
    private Map<String, Object> templateParams;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
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
