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
    Binding mailBinding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(EQueue.QUEUE_MAIL.getRouteKey());
    }
}
