package com.example.bio.common.component;

import com.example.bio.common.constant.EQueue;
import com.example.bio.model.UserActiveToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangfuqi
 * @date 2020/11/10
 */
@Component
@Slf4j
public class MyMailSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMail(UserActiveToken activeToken) {
        amqpTemplate.convertAndSend(EQueue.QUEUE_MAIL.getExchange(), EQueue.QUEUE_MAIL.getRouteKey(), activeToken, message -> message);
        log.info("send mail userId:{}", activeToken.getUser().getId());
    }
}
