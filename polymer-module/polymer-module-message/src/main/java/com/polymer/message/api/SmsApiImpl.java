package com.polymer.message.api;

import com.polymer.api.message.SmsApi;
import com.polymer.message.cache.SmsSendCache;
import com.polymer.message.sms.service.SmsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务Api
 *
 * @author polymer
 */
@Component
public class SmsApiImpl implements SmsApi {
    @Resource
    private SmsService smsService;
    @Resource
    private SmsSendCache smsSendCache;

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param params 参数
     * @return 是否发送成功
     */
    @Override
    public boolean send(String mobile, Map<String, String> params) {
        return smsService.send(mobile, params);
    }

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param key    参数KEY
     * @param value  参数Value
     * @return 是否发送成功
     */
    @Override
    public boolean sendCode(String mobile, String key, String value) {
        // 短信参数
        Map<String, String> params = new HashMap<>();
        params.put(key, value);

        // 发送短信
        boolean flag = smsService.send(mobile, params);
        if (flag) {
            smsSendCache.saveCode(mobile, value);
        }
        return flag;
    }

    /**
     * 效验短信验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return 是否效验成功
     */
    @Override
    public boolean verifyCode(String mobile, String code) {
        String value = smsSendCache.getCode(mobile);
        if (value != null) {
            // 删除短信验证码
            smsSendCache.deleteCode(mobile);

            // 效验
            return value.equalsIgnoreCase(code);
        }

        return false;
    }
}
