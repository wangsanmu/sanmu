package com.wangsen.user.config.druid;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author wangsen
 * @data 2018/4/15 10:01
 */

@Aspect
@Component
public class DataSourceAop {

    private static final Logger log = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("execution( * com.wangsen.user.mapper.*.*(..))")
    public void dataSourcePointCut() {
    }

    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?>[] clazz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = clazz[0].getMethod(method, parameterTypes);
            //如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
            if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource data = m.getAnnotation(TargetDataSource.class);
                String dataSourceName = data.value();
                DbContextHolder.setDbType(DBTypeEnum.valueOf(dataSourceName));
                log.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
            } else {
                //如果没有就按照插入用主库，读用辅助库
                if(m.getName().startsWith("select")){
                    DbContextHolder.setDbType(DBTypeEnum.slave);
                }else{
                    DbContextHolder.setDbType(DBTypeEnum.master);
                }
            }
        } catch (Exception e) {
            log.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
        }
    }


    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint){
        DbContextHolder.clearDbType();
    }

}
