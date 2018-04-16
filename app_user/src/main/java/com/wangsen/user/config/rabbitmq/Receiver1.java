package com.wangsen.user.config.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangsen
 * @data 2018/4/16 21:41
 */
@Component
@RabbitListener(queues = "topic.message")
public class Receiver1 {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1 : " + hello);
    }

}
