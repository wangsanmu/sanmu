package com.wangsen.user.config.rabbitmq;

import com.wangsen.user.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author wangsen
 * @data 2018/4/15 21:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageProducerTest {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageConsumer messageConsumer;

    @Autowired
    private Receiver1 receiver1;

    @Autowired
    private Receiver2 receiver2;

    @Autowired
    private FanoutReceiveA fanoutReceiveA;

    @Autowired
    private FanoutReceiveB fanoutReceiveB;

    @Autowired
    private FanoutReceiveC fanoutReceiveC;

    @Test
    public void send() {
        for (int i=0;i<100;i++){
            messageProducer.send(i);
        }
    }

    /**
     * Direct：direct 类型的行为是"先匹配,
     */
    @Test
    public void sendObject(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("王森");
        messageProducer.sendObject(sysUser);
    }

    /*
     * Topic：按规则转发消息（最灵活）
     */
    @Test
    public void send1(){
        messageProducer.send1();
    }

    @Test
    public void send2(){
        messageProducer.send2();
    }

    /**
     * 测试订阅
     */
    @Test
    public void sendFanout(){
        messageProducer.sendFanout();
    }

}