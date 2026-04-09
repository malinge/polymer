package com.polymer.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.message.vo
 * CreateTime: 2024-08-02  14:53
 * Description: SSE消息体
 *
 * @author polymer
 * @version 2.0
 */
@Schema(description = "sse消息体")
public class MessageSseSendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "消息")
    private String msg;

    @Schema(description = "代理名称")
    private String agentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
