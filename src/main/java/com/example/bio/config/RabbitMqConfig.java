package com.example.bio.config;

import com.example.bio.common.constant.EQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhangfuqi
 * @date 2020/11/10
 */
@Configuration
@Slf4j
@EnableAsync
public class RabbitMqConfig {
    @Bean
    DirectExchange mailDirect() {
        return ExchangeBuilder
                .directExchange(EQueue.QUEUE_MAIL.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(EQueue.QUEUE_MAIL.getName());
    }


    @Bean
    DirectExchange mailResetDirect() {
        return ExchangeBuilder
                .directExchange(EQueue.QUEUE_MAIL.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue mailResetQueue() {
        return new Queue(EQueue.QUEUE_RESET_MAIL.getName());
    }

    @Bean
    Binding mailBinding(DirectExchange mailDirect, Queue mailQueue) {
        return BindingBuilder
                .bind(mailQueue)
                .to(mailDirect)
                .with(EQueue.QUEUE_MAIL.getRouteKey());
    }

    @Bean
    Binding resetMailBinding(DirectExchange mailResetDirect, Queue mailResetQueue) {
        return BindingBuilder
                .bind(mailResetQueue)
                .to(mailResetDirect)
                .with(EQueue.QUEUE_RESET_MAIL.getRouteKey());
    }
}
