package com.polymer.message.api;

import com.polymer.api.message.MailSendApi;
import com.polymer.message.mail.service.MailSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 邮件发送 API 实现类
 *
 * @author wangjingyi
 */
@Service
public class MailSendApiImpl implements MailSendApi {
    @Resource
    private MailSendService mailSendService;

    /**
     * 发送单条邮箱给 Member 用户
     * 在 mail 为空时，使用 userId 加载对应 Member 的邮箱
     *
     * @param mail           邮箱
     * @param userId         用户id
     * @param templateCode   模板code
     * @param templateParams 模板参数
     * @return 发送日志编号
     */
    @Override
    public Long sendSingleMailToUser(String mail, Long userId,
                                     String templateCode, Map<String, Object> templateParams) {
        return mailSendService.sendSingleMailToUser(mail, userId,
                templateCode, templateParams);
    }


}
