package com.example.bio.service.impl;

import com.example.bio.common.constant.CommonConstant;
import com.example.bio.model.User;
import com.example.bio.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author zhangfuqi
 * @date 2020/10/25
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("发送失败~");
        }

    }

    @Override
    public void sendActiveMail(User user, String token) {
        Context context = new Context();
        context.setVariable("requestURL", CommonConstant.ACTIVE_MAIL_URL + token);
        String content = templateEngine.process("welcome", context);
        sendHtmlMessage(user.getEmail(), "welcome to autobiography", content);
    }

    @Override
    public void sendResetPasswordEmail(String email, String token) {
        Context context = new Context();
        context.setVariable("token", token);
        String content = templateEngine.process("reset", context);
        sendHtmlMessage(email, "Reset Your Password", content);
    }
}
