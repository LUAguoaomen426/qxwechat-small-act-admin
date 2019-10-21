package com.red.star.macalline.act.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.config
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-21 14:58
 * @Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactoryBean",
        transactionManagerRef = "primaryTransactionManager",
        //设置Repository所在位置
        basePackages = {"com.red.star.macalline.act.admin.modules.system.repository", "com.red.star.macalline.act.admin.repository"})
public class PrimaryDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Bean(name = "primaryEntityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean billSystemEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(primaryDataSource)
                .properties(getVendorProperties())
                //设置实体类所在位置
                .packages("com.red.star.macalline.act.admin.modules.system.domain", "com.red.star.macalline.act.admin.domain")
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    private Map<String, String> getVendorProperties() {
        return jpaProperties.getProperties();
    }

    /**
     * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
     * 总之,在执行操作之前,我们总要获取一个EntityManager,这就类似于Hibernate的Session,
     * mybatis的sqlSession.
     *
     * @param builder
     * @return
     */
    @Bean(name = "primaryEntityManagerFactory")
    public EntityManagerFactory billSystemEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return this.billSystemEntityManagerFactoryBean(builder).getObject();
    }

    /**
     * 配置事物管理器
     */
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager billSystemTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(billSystemEntityManagerFactory(builder));
    }
}
