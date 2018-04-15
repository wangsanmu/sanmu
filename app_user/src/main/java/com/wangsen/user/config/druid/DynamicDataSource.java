package com.wangsen.user.config.druid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author wangsen
 * @data 2018/4/14 19:28
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    /**
     * 取得当前使用哪个数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("数据源为{"+DbContextHolder.getDbType()+"}");
        return DbContextHolder.getDbType();
    }

}
