package com.example.bio.common.component;

import com.example.bio.model.UserActiveToken;
import com.example.bio.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangfuqi
 * @date 2020/11/10
 */
@Component
@Slf4j
@RabbitListener(queues = "bio.mail")
public class MailReceiver {
    @Autowired
    private MailService mailService;

    @RabbitHandler
    public void handle(UserActiveToken activeToken) {
        log.info("receive mail userId:{}", activeToken.getUser().getId());
        mailService.sendActiveMail(activeToken.getUser(), activeToken.getToken());
    }

}