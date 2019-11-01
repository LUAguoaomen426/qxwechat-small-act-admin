package com.red.star.macalline.act.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.config
 * @Description: 数据源配置：
 * @Author: AMGuo
 * @CreateDate: 2019-10-21 14:56
 * @Version: 1.0
 */
@Configuration
public class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * mybatis读写分离
     *
     * @return
     */
    @Bean
    public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin() {
        return new MasterSlaveAutoRoutingPlugin();
    }

    /**
     * jpa读写分离（根据事务readonly属性动态切换读写数据源）
     *
     * @return
     */
    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", primaryDataSource());
        targetDataSources.put("slave", slaveDataSource());

        AbstractRoutingDataSource dynamicDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                String lookupKey = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "slave" : "master";
                LOGGER.trace("[jpa]connected DataSource :{}", lookupKey);
                return lookupKey;
            }
        };
        dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get("master"));
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(dynamicDataSource());
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws IOException {
        return builder
                .dataSource(dataSource())
                .packages("com.red.star.macalline.act.admin")
                .build();
    }

    @Bean(name = "transactionManager")
    JpaTransactionManager transactionManager(EntityManagerFactoryBuilder builder) throws IOException {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }

}
