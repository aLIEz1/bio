package com.example.bio.common.component;

import com.example.bio.model.PasswordResetToken;
import com.example.bio.model.UserActiveToken;
import com.example.bio.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangfuqi
 * @date 2020/11/10
 */
@Component
public class MailReceiver {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MailReceiver.class);

    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = "bio.mail")
    @RabbitHandler
    public void handle(UserActiveToken activeToken) {
        log.info("receive mail userId:{}", activeToken.getUser().getId());
        mailService.sendActiveMail(activeToken.getUser(), activeToken.getToken());
    }

    @RabbitListener(queues = "bio.reset.mail")
    @RabbitHandler
    public void handleReset(PasswordResetToken resetToken) {
        log.info("receive mail :{}", resetToken.getEmail());
        mailService.sendResetPasswordEmail(resetToken.getEmail(), resetToken.getToken());
    }

}
