package com.wangsen.user.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangsen
 * @data 2018/4/15 20:47
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue firstQueue(){
        return new Queue("firstQueue");
    }
}
