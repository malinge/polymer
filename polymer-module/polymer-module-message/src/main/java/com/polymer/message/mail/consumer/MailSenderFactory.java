package com.polymer.message.mail.consumer;

import com.polymer.message.vo.MessageMailAccountVO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailSenderFactory {
    public static JavaMailSender createMailSender(MessageMailAccountVO account) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(account.getHost());
        sender.setPort(account.getPort());
        sender.setUsername(account.getUsername());
        sender.setPassword(account.getPassword());
        sender.setProtocol(account.getProtocol());

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", account.getSslEnable());
        props.put("mail.debug", "true"); // 调试模式

        return sender;
    }
}
