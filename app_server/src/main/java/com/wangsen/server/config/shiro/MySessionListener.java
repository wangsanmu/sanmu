package com.wangsen.server.config.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.context.annotation.Configuration;
/**
 * @author wangsen
 * @data 2018/3/25 16:02
 */
@Configuration
public class MySessionListener implements SessionListener{
    @Override
    public void onStart(Session session) {
        System.out.println("会话创建："+ session.getId() + "      会话主机" + session.getHost() );
    }

    @Override
    public void onStop(Session session) {
        System.out.println("会话停止：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("会话过期：" + session.getId());
    }
}
