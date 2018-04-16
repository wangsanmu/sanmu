package com.wangsen.user.config.rabbitmq;

import com.wangsen.user.entity.SysUser;
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

    public void send(int i){
        LocalDateTime current =LocalDateTime.now();
        System.out.println("Send Message : "+current);
        rabbitTemplate.convertAndSend("firstQueue","Send Message"+ current + "你好王森"+i);
    }

    /**
     * 发送对象
     */
    public void sendObject(SysUser sysUser){
        rabbitTemplate.convertAndSend("firstQueue",sysUser);
    }

    /**
     *测试灵活对象
     */
    public void send1() {
        String context = "hi, i am message 1";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", context);
    }

    public void send2() {
        String context = "hi, i am messages 2";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", context);
    }

    public void sendFanout() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
    }

}
