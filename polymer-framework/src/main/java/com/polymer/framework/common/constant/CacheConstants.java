package com.polymer.framework.common.constant;

/**
 * 缓存的key 常量
 *
 * @author polymer
 */
public class CacheConstants {
    /**
     * 登录用户 redis key
     */
    public static final String SYS_TOKEN_KEY = "sys_token:";

    /**
     * 验证码 redis key
     */
    public static final String SYS_CAPTCHA_KEY = "sys_captcha:";

    /**
     * userid Key
     */
    public static final String SYS_USERID_KEY = "sys_userId:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 参数管理 KEY
     */
    public static final String SYSTEM_PARAMS_KEY = "system_params";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 字典管理 cache name
     */
    public static final String SYS_DICT_CACHE = "sys-dict";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 签名随机数 cache key
     */
    public static final String SIGN_NONCE_KEY = "sign_nonce:";

    /**
     * appid管理 cache key
     */
    public static final String SYS_APPID_KEY = "sys_appid:";

}
