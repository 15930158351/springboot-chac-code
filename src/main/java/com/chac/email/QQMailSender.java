package com.chac.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class QQMailSender {

    public static final String SENDER = "10369446@qq.com";

    public static void main(String[] args) {
        // 配置邮件服务器属性
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", "587");

        // 创建会话
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, "qknnlojumeafcbbd");
            }
        });

        try {
            // 创建默认的MimeMessage对象
            Message message = new MimeMessage(session);
            // 设置发件人
            message.setFrom(new InternetAddress(SENDER));
            // 设置收件人 TODO
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("stephen.chac@goldentec.com"));

            //TODO 3
            String companyName = "英才网联（北京）科技有限公司";
            String appKey = "111";
            Integer merchantId = 223;
            // 设置邮件主题
            message.setSubject("【有活岗位生产环境秘钥】" + companyName + "_" + merchantId);

//            String companyName = "公司名";

            String contentText = "【" + companyName + "】 您好：\n" +
                    "\n" +
                    "以下是有活平台生产环境API对接信息：\n" +
                    "【app_key】\n" +
                    appKey + "\n" +
                    "【app_secret】\n" +
                    "c38010495ee07ffd689e0d35da63a3d8\n" +
                    "【aes_iv】\n" +
                    "93b93143cafab46c\n\n" +
                    "请查收，有问题可在对接群里随时反馈。";
            // 设置邮件内容
            message.setText(contentText);

            // 发送邮件
            Transport.send(message);

            System.out.println("邮件发送成功！");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
