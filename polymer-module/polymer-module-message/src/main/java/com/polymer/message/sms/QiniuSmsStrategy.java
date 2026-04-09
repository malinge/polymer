package com.polymer.message.sms;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.message.sms.config.SmsConfig;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;

import java.util.Map;

/**
 * 七牛云短信
 *
 * @author polymer
 */
public class QiniuSmsStrategy implements SmsStrategy {
    private final SmsConfig smsConfig;
    private final SmsManager smsManager;

    public QiniuSmsStrategy(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;

        Auth auth = Auth.create(smsConfig.getAccessKey(), smsConfig.getSecretKey());
        smsManager = new SmsManager(auth);
    }

    @Override
    public void send(String mobile, Map<String, String> params) {
        try {
            Response response = smsManager.sendSingleMessage(smsConfig.getTemplateId(), mobile, params);

            // 是否发送成功
            if (!response.isOK()) {
                throw new ServiceException(response.error);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
