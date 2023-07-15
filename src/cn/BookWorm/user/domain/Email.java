package cn.BookWorm.user.domain;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class Email implements Runnable{
    Properties properties = null; //参数配置
    Session session = null; //会话对象
    MimeMessage message = null; //邮件对象
    Transport transport = null; //邮件传输对象

    String from = "clear_poem@163.com"; //发件人邮箱地址
    String username = "clear_poem@163.com"; //发件人称号，同邮箱地址
    String password = "VVWFOCOCVIQGORCE"; //发件人邮箱客户端授权码

    String to; //收件人邮箱地址
    String subject; //邮件主题
    String content; //邮件内容

    public Email(String to, String subject, String text)
    {
        this.to = to;
        this.content = text;
        this.subject = subject;
    }

    public void initProperties()
    {
        properties = new Properties();
        properties.setProperty("mail.stmp.host", "smtp.163.com");
        properties.setProperty("mail.stmp.auth", "true");
    }

    public void initSession()
    {
        session = Session.getDefaultInstance(properties);
        session.setDebug(true); //在控制台console上看到发送邮件的过程
    }

    public void initMessage()
    {
        message = new MimeMessage(session);

        try {
            //设置发件人
            message.setFrom(new NewsAddress(from));
            //收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            MimeMultipart multipart = new MimeMultipart(); //添加邮件的各个部分内容，包括文本内容和附件
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            multipart.setSubType("mixed"); //混合关系
            message.setContent(multipart);
            //设置发件时间
            message.setSentDate(new Date());
            //保存上面的所有设置
            message.saveChanges();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTransport()
    {
        //根据 Session 获取邮件传输对象
        try {
            transport = session.getTransport("smtp");
            transport.connect("smtp.163.com", username, password);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send()
    {
        try {
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run()
    {
        initProperties();
        initSession();
        initMessage();
        initTransport();

        send();
    }
}
