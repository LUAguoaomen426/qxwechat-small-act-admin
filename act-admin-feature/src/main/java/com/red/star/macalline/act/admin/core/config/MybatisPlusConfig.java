package com.red.star.macalline.act.admin.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.core.config
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-11-13 19:26
 * @Version: 1.0
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
