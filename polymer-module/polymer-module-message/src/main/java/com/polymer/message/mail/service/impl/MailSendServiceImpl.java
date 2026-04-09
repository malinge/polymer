package com.polymer.message.mail.service.impl;

import com.polymer.api.system.SysUserApi;
import com.polymer.api.system.dto.SysUserDTO;
import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.enums.UserTypeEnum;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.message.mail.consumer.MailSenderFactory;
import com.polymer.message.mail.service.MailSendService;
import com.polymer.message.service.MessageMailAccountService;
import com.polymer.message.service.MessageMailLogService;
import com.polymer.message.service.MessageMailTemplateService;
import com.polymer.message.vo.MessageMailAccountVO;
import com.polymer.message.vo.MessageMailSendMessage;
import com.polymer.message.vo.MessageMailTemplateVO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮箱发送 Service 实现类
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@Service
@Validated
public class MailSendServiceImpl implements MailSendService {
    @Resource
    private SysUserApi sysUserApi;
    @Resource
    private MessageMailAccountService messageMailAccountService;
    @Resource
    private MessageMailTemplateService messageMailTemplateService;
    @Resource
    private MessageMailLogService messageMailLogService;

    /**
     * 发送单条邮件给用户 APP 的用户
     *
     * @param mail           邮箱
     * @param userId         用户编码
     * @param templateCode   邮件模版编码
     * @param templateParams 邮件模版参数
     * @return 发送日志编号
     */
    @Override
    public Long sendSingleMailToUser(String mail, Long userId,
                                     String templateCode, Map<String, Object> templateParams) {
        // 如果 mail 为空，则加载用户编号对应的邮箱
        if (StringUtils.isEmpty(mail)) {
            SysUserDTO user = sysUserApi.findUserByUserid(userId);
            if(user != null){
                mail = user.getEmail();
            }

        }
        // 执行发送
        return sendSingleMail(mail, userId, UserTypeEnum.MEMBER.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleMail(String mail, Long userId, Integer userType,
                               String templateCode, Map<String, Object> templateParams) {
        // 校验邮箱模版是否合法
        MessageMailTemplateVO template = validateMailTemplate(templateCode);
        // 校验邮箱账号是否合法
        MessageMailAccountVO account = validateMailAccount(template.getAccountId());

        // 校验邮箱是否存在
        mail = validateMail(mail);
        validateTemplateParams(template, templateParams);

        // 创建发送日志。如果模板被禁用，则不发送短信，只记录日志
        Boolean isSend = Constant.ENABLE.equals(template.getStatus());
        String title = replacePlaceholders(template.getTitle(), templateParams);
        String content = replacePlaceholders(template.getContent(), templateParams);
        Long sendLogId = messageMailLogService.createMailLog(userId, userType, mail,
                account, template, content, templateParams, isSend);
        // 发送 MQ 消息，异步执行发送短信
        if (isSend) {
            MessageMailSendMessage message = new MessageMailSendMessage();
            message.setLogId(sendLogId);
            message.setMail(mail);
            message.setAccountId(account.getId());
            message.setNickname(template.getNickname());
            message.setTitle(title);
            message.setContent(content);
            message.setSubject(template.getSubject());

            doSendMail(message);
        }
        return sendLogId;
    }

    private String replacePlaceholders(String template, Map<String, Object> values) {
        Pattern pattern = Pattern.compile("\\{(\\w+)}");
        Matcher matcher = pattern.matcher(template);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = String.valueOf(values.getOrDefault(key, "{" + key + "}"));
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    @Override
    public void doSendMail(MessageMailSendMessage message) {
        // 1. 创建发送账号
        MessageMailAccountVO account = validateMailAccount(message.getAccountId());
        String from = StringUtils.isNotEmpty(message.getNickname()) ? message.getNickname() + "<" + account.getMail() + ">" : account.getMail();
        JavaMailSender mailSender = MailSenderFactory.createMailSender(account);
        try {
            MimeMessage mm = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mm, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(message.getMail());
            helper.setSubject(message.getSubject());

            helper.setText(message.getContent(), true);

            mailSender.send(mm);
            // 3. 更新结果（成功）
            messageMailLogService.updateMailSendResult(message.getLogId(), null, null);
        } catch (Exception e) {
            // 3. 更新结果（异常）
            messageMailLogService.updateMailSendResult(message.getLogId(), null, e);
        }
    }

    private MessageMailTemplateVO validateMailTemplate(String templateCode) {
        // 获得邮件模板。考虑到效率，从缓存中获取
        MessageMailTemplateVO template = messageMailTemplateService.fildMailTemplateByCode(templateCode);
        // 邮件模板不存在
        if (template == null) {
            throw new RuntimeException("邮件模版不存在");
        }
        return template;
    }

    private MessageMailAccountVO validateMailAccount(Long accountId) {
        // 获得邮箱账号。考虑到效率，从缓存中获取
        MessageMailAccountVO account = messageMailAccountService.getById(accountId);
        // 邮箱账号不存在
        if (account == null) {
            throw new RuntimeException("邮箱账号不存在");
        }
        return account;
    }

    private String validateMail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            throw new RuntimeException("邮箱不存在");
        }
        return mail;
    }

    /**
     * 校验邮件参数是否确实
     *
     * @param template       邮箱模板
     * @param templateParams 参数列表
     */
    private void validateTemplateParams(MessageMailTemplateVO template, Map<String, Object> templateParams) {
        template.getParams().forEach(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw new RuntimeException("模板参数key:"+key+"缺失");
            }
        });
    }

}
