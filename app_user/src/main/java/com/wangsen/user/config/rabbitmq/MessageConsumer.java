package com.wangsen.user.config.rabbitmq;

import com.wangsen.user.entity.SysUser;
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

    @RabbitHandler
    public void process1(SysUser sysUser){
        System.out.println("Receiver object : " + sysUser);
    }

}
