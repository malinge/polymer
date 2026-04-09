package com.polymer.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.system.vo
 * CreateTime: 2023-08-18  15:56
 * Description: 用户token
 *
 * @author polymer
 * @version 2.0
 */
@Schema(description = "AccessToken")
public class AccessTokenVO implements Serializable {
    //@Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "access_token")
    @JsonProperty(value = "access_token")
    private String accessToken;

    @Schema(description = "access_token 过期时间")
    private LocalDateTime accessTokenExpire;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(LocalDateTime accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }
}

