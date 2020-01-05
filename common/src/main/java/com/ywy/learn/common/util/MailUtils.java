package com.ywy.learn.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ve
 * @date 2020/1/5 14:36
 */
@Slf4j
public class MailUtils {


    static String EMAIL = "v834250018@163.com";
    static String AUTH_CODE = "JyY4i9HG7hRkB5fT";
    static String EMAIL_HOST = "smtp.163.com";

    public static void sendMail(String subject, String content, String... addressees) {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.enable", "true");
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", EMAIL_HOST);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, AUTH_CODE);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(EMAIL));
            List<InternetAddress> list = new ArrayList();
            for (String addressee : addressees) {
                list.add(new InternetAddress(addressee));
            }
            message.addRecipients(Message.RecipientType.TO, list.toArray(new Address[0]));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送失败", e);
        }
    }

}