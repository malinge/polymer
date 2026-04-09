package com.polymer.system.entity;

import java.io.Serializable;

/**
 * 图片验证码
 *
 * @author polymer
 */
//图片验证码
public class SysCaptchaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //key
    private String key;

    //image base64
    private String image;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
