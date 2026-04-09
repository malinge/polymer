package com.polymer.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.system.vo
 * CreateTime: 2023-08-18  15:57
 * Description: 用户token
 *
 * @author polymer
 * @version 2.0
 */
@Schema(description = "用户Token")
public class SysUserTokenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "access_token")
    @JsonProperty(value = "access_token")
    private String accessToken;

    @Schema(description = "refresh_token")
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @Schema(description = "access_token 过期时间")
    private LocalDateTime accessTokenExpire;

    @Schema(description = "refresh_token 过期时间")
    private LocalDateTime refreshTokenExpire;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(LocalDateTime accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public LocalDateTime getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(LocalDateTime refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }
}

