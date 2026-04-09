package com.polymer.message.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 邮箱账号表查询
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Schema(description = "邮箱账号表查询")
public class MessageMailAccountQuery extends PageParam {
    @Schema(description = "邮箱")
    private String mail;

    @Schema(description = "用户名")
    private String username;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}