package com.wangsen.user.config.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangsen
 * @data 2018/4/16 21:55
 */
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiveB {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("FanoutReceiveB : " + hello);
    }


}
