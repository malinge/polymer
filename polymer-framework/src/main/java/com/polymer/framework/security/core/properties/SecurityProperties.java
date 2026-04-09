package com.polymer.framework.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 安全配置项
 *
 * @author polymer
 */
@Component
@ConfigurationProperties(prefix = "polymer.security")
public class SecurityProperties {
    /**
     * accessToken 过期时间(单位：秒)，默认2小时
     */
    private int accessTokenExpire = 60 * 60 * 2;
    /**
     * refreshToken 过期时间(单位：秒)，默认14天
     */
    private int refreshTokenExpire = 60 * 60 * 24 * 14;
    /**
     * 开启同一用户异地登录踢人功能，true开启，false关闭。
     */
    private Boolean kickOut = false;

    public int getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(int accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public int getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(int refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public Boolean getKickOut() {
        return kickOut;
    }

    public void setKickOut(Boolean kickOut) {
        this.kickOut = kickOut;
    }
}
