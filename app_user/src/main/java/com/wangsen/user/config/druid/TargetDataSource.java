package com.wangsen.user.config.druid;

import java.lang.annotation.*;

/**
 * @author wangsen
 * @data 2018/4/15 10:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TargetDataSource {
    String value();//此处接收的是数据源的名称
}