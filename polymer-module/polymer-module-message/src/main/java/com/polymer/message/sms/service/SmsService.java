package com.polymer.message.sms.service;

import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.ExceptionUtils;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.message.service.MessageSmsLogService;
import com.polymer.message.service.MessageSmsPlatformService;
import com.polymer.message.sms.SmsContext;
import com.polymer.message.sms.config.SmsConfig;
import com.polymer.message.vo.MessageSmsLogVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信服务
 *
 * @author polymer
 */

@Service
public class SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsService.class);
    @Resource
    private MessageSmsPlatformService messageSmsPlatformService;
    @Resource
    private MessageSmsLogService messageSmsLogService;

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @return 是否发送成功
     */
    public boolean send(String mobile) {
        return this.send(mobile, new HashMap<>());
    }

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param params 参数
     * @return 是否发送成功
     */
    public boolean send(String mobile, Map<String, String> params) {
        SmsConfig config = roundSmsConfig();

        try {
            // 发送短信
            new SmsContext(config).send(mobile, params);

            saveLog(config, mobile, params, null);
            return true;
        } catch (Exception e) {
            log.error("短信发送失败，手机号：{}", mobile, e);

            saveLog(config, mobile, params, e);

            return false;
        }
    }

    /**
     * 保存短信日志
     */
    public void saveLog(SmsConfig config, String mobile, Map<String, String> params, Exception e) {
        MessageSmsLogVO logVO = new MessageSmsLogVO();
        logVO.setPlatform(config.getPlatform());
        logVO.setPlatformId(config.getId());
        logVO.setMobile(mobile);
        logVO.setParams(JsonUtils.toJsonString(params));

        if (e != null) {
            String error = StringUtils.substring(ExceptionUtils.getExceptionMessage(e), 0, 2000);
            logVO.setStatus(Constant.FAIL);
            logVO.setError(error);
        } else {
            logVO.setStatus(Constant.SUCCESS);
        }

        messageSmsLogService.save(logVO);
    }

    /**
     * 通过轮询算法，获取短信平台的配置
     */
    private SmsConfig roundSmsConfig() {
        List<SmsConfig> platformList = messageSmsPlatformService.listByEnable();

        // 是否有可用的短信平台
        int count = platformList.size();
        if (count == 0) {
            throw new ServiceException("没有可用的短信平台，请先添加");
        }

        return platformList.get(0);
    }

}
