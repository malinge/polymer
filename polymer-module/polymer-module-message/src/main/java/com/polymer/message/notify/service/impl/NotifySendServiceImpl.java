package com.polymer.message.notify.service.impl;

import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.message.notify.service.NotifySendService;
import com.polymer.message.service.MessageNotifyMessageService;
import com.polymer.message.service.MessageNotifyTemplateService;
import com.polymer.message.vo.MessageNotifyTemplateVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.message.notify.service
 * CreateTime: 2024-04-10  10:19
 * Description: 站内信发送
 *
 * @author polymer
 * @version 2.0
 */
@Service
public class NotifySendServiceImpl implements NotifySendService {
    @Resource
    private MessageNotifyTemplateService messageNotifyTemplateService;
    @Resource
    private MessageNotifyMessageService messageNotifyMessageService;

    /**
     * description: 批量发送消息
     * date: 2024/4/10 9:45
     * param [userId, templateCode, templateParams]
     *
     * @author polymer
     **/
    @Override
    public void sendBatchNotify(List<Long> userIds, String templateCode, Map<String, Object> templateParams) {
        // 校验模版
        MessageNotifyTemplateVO templateVO = messageNotifyTemplateService.validateNotifyTemplate(templateCode);
        if (templateVO == null) {
            throw new RuntimeException("站内信模版不存在");
        }
        if (Constant.DISABLE.equals(templateVO.getStatus())) {
            throw new RuntimeException("站内信模版已经关闭，无法给用户发送");
        }
        // 校验参数
        validateTemplateParams(templateVO, templateParams);

        // 发送站内信
        String content = StringUtils.replaceTemplate(templateVO.getContent(), templateParams);
        messageNotifyMessageService.createNotifyMessage(userIds, templateVO, content, templateParams);

    }

    /**
     * 校验站内信模版参数是否确实
     *
     * @param template       邮箱模板
     * @param templateParams 参数列表
     */
    public void validateTemplateParams(MessageNotifyTemplateVO template, Map<String, Object> templateParams) {
        template.getParams().forEach(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw new RuntimeException("模板参数缺失");
            }
        });
    }
}
