package com.polymer.message.api;

import com.polymer.api.message.NotifyMessageSendApi;
import com.polymer.message.notify.service.NotifySendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.message.api
 * CreateTime: 2024-04-10  09:56
 * Description: 站内信消息发送
 *
 * @author polymer
 * @version 2.0
 */
@Service
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {
    @Resource
    private NotifySendService notifySendService;

    /**
     * description: 批量发送消息
     * date: 2024/4/10 9:45
     * param [userId, templateCode, templateParams]
     *
     * @author polymer
     **/
    @Override
    public void sendBatchNotify(List<Long> userIds, String templateCode, Map<String, Object> templateParams) {
        notifySendService.sendBatchNotify(userIds, templateCode, templateParams);
    }
}
