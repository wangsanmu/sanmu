package com.wangsen.user.config.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author wangsen
 * @data 2018/4/15 21:23
 */
@Component
public class MessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(){
        LocalDateTime current =LocalDateTime.now();
        System.out.println("Send Message : "+current);
        rabbitTemplate.convertAndSend("firstQueue","Send Message"+ current + "你好王森");
    }
}
