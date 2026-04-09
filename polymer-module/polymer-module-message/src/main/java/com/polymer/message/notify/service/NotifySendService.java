package com.polymer.message.notify.service;

import java.util.List;
import java.util.Map;

/**
 * 站内信发送 Service 接口
 *
 * @author polymer
 */
public interface NotifySendService {

    /**
     * description: 批量发送消息
     * date: 2024/4/10 9:45
     * param [userId, templateCode, templateParams]
     *
     * @author polymer
     **/
    void sendBatchNotify(List<Long> userIds, String templateCode,
                         Map<String, Object> templateParams);

}
