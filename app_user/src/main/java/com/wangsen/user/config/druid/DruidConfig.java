package com.wangsen.user.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangsen
 * @data 2018/3/8 13:40
 */
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.master.url}")
    private String masterurl;

    @Value("${spring.datasource.slave.url}")
    private String slaveurl;

    @Value("${spring.datasource.master.password}")
    private String masterpassword;

    @Value("${spring.datasource.slave.password}")
    private String slavepassword;

    @Value("${spring.datasource.master.username}")
    private String masterusername;

    @Value("${spring.datasource.slave.username}")
    private String slaveusername;

    @Autowired
    private DruidConfigOrtherConfig druidConfigOrtherConfig;

    @Bean(name = "master")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            dataSource.setUrl(masterurl);
            dataSource.setPassword(masterpassword);
            dataSource.setUsername(masterusername);
            druidConfigOrtherConfig.mysqlDataSource(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix="spring.datasource.slave")
    public DataSource salveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            dataSource.setUrl(slaveurl);
            dataSource.setPassword(slavepassword);
            dataSource.setUsername(slaveusername);
            druidConfigOrtherConfig.mysqlDataSource(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "dataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier(value = "master") DataSource master,
                                               @Qualifier(value = "slave") DataSource slave){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(master);
        Map<Object,Object> map = new HashMap<Object, Object>();
        map.put("master",master);
        map.put("slave",slave);
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }
}
