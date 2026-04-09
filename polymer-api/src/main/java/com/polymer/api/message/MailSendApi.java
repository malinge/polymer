package com.polymer.api.message;

import java.util.Map;

/**
 * 邮箱发送 API 接口
 *
 * @author polymer
 */
public interface MailSendApi {

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
    Long sendSingleMailToUser(String mail, Long userId,
                              String templateCode, Map<String, Object> templateParams);

}
