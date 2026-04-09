package com.polymer.framework.encrypt.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * api解密属性配置类
 *
 * @author polymer
 */
@ConfigurationProperties(prefix = "polymer.api-decrypt")
public class ApiDecryptProperties {

    /**
     * 加密开关
     */
    private Boolean enabled;

    /**
     * 头部标识
     */
    private String headerFlag;

    /**
     * 响应加密公钥
     */
    private String publicKey;

    /**
     * 请求解密私钥
     */
    private String privateKey;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getHeaderFlag() {
        return headerFlag;
    }

    public void setHeaderFlag(String headerFlag) {
        this.headerFlag = headerFlag;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
