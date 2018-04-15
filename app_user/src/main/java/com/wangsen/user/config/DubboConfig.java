package com.wangsen.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wangsen
 * @data 2018/3/24 9:51
 */
@Configuration
//@PropertySource("classpath:dubbo/dubbo.properties")
@PropertySource("classpath:log4j.properties")
//@ImportResource({ "classpath:dubbo/*.xml" })
public class DubboConfig {
}
