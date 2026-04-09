package com.polymer.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.system.entity
 * CreateTime: 2023-08-18  15:47
 * Description: 用户token
 *
 * @author polymer
 * @version 2.0
 */
public class SysUserTokenEntity {

    //id
    private Long id;

    //用户ID
    private Long userId;

    //accessToken
    @JsonProperty(value = "access_token")
    private String accessToken;

    //accessToken 过期时间
    private LocalDateTime accessTokenExpire;

    //refreshToken
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    //refreshToken 过期时间
    private LocalDateTime refreshTokenExpire;

    //创建时间
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(LocalDateTime refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
