package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 图片验证码
 *
 * @author polymer
 */
@Schema(description = "图片验证码")
public class SysCaptchaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "key")
    private String key;

    @Schema(description = "image base64")
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
