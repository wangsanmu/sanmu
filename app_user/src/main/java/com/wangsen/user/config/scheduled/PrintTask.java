package com.wangsen.user.config.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wangsen
 * @data 2018/4/19 9:35
 * @des
 */
@EnableScheduling
@Component
public class PrintTask {

    @Scheduled(cron = "*/5 * * * * ?")
    public void cron(){
        System.out.println("执行测试时间："+ new Date(System.currentTimeMillis()));
    }
}
