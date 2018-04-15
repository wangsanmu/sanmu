package com.wangsen.user.config.mybatisplus;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.GlobalConfig;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author wangsen
 * @data 2018/3/8 19:22
 */
@Configuration
@MapperScan(value = "com.wangsen.user.mapper*")
public class MyBatisConfig {

    private Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Autowired
    private MybatisProperties properties;

//    @Autowired
//    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Resource(name = "dataSource")
    private DataSource dataSource;


    @Resource
    private MyMetaObjectHandler myMetaObjectHandler;

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(){
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setVfs(SpringBootVFS.class);
        mybatisPlus.setGlobalConfig(globalConfig());
        mybatisPlus.setVfs(SpringBootVFS.class);
//        if (StringUtils.hasText(this.properties.getConfigLocation())) {
//            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//        }
        mybatisPlus.setConfiguration(properties.getConfiguration());
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            mybatisPlus.setPlugins(this.interceptors);
        }
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlus.setConfiguration(mc);
//        if (this.databaseIdProvider != null) {
//            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
//        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
        }
        return mybatisPlus;
    }


    @Bean
    public GlobalConfiguration globalConfig(){
        GlobalConfiguration globalConfig = new GlobalConfiguration();
        //#主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
        globalConfig.setIdType(2);
        //#字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
        globalConfig.setFieldStrategy(2);
        //#驼峰下划线转换
        globalConfig.setDbColumnUnderline(true);
        //#刷新mapper 调试神器
        globalConfig.setRefresh(true);
        //#数据库大写下划线转换
        globalConfig.setCapitalMode(true);
        // #逻辑删除配置（下面3个配置）
        globalConfig.setLogicDeleteValue("0");
        globalConfig.setLogicNotDeleteValue("1");
//        globalConfig.setIdentifierQuote("com.baomidou.mybatisplus.mapper.LogicSqlInjector");
        //自定义填充策略接口实现,不在推荐使用此方式进行配置,请使用自定义bean注入
        globalConfig.setMetaObjectHandler(myMetaObjectHandler);
        return globalConfig;
    }


    /**
     *	 mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        page.setLocalPage(true);
        return page;
    }


    /**
     * SQL执行效率插件
     */
    @Bean(name = "performanceInterceptor")
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor =  new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);
        return performanceInterceptor;
    }

    /**
     * 事务管理
     */
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager((DataSource)SpringContextUtil.getBean("dataSource"));
    }
}
