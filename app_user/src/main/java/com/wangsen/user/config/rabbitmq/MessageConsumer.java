package com.wangsen.user.config.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangsen
 * @data 2018/4/15 21:28
 */
@Component
@RabbitListener(queues = "firstQueue")
public class MessageConsumer {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }

}
