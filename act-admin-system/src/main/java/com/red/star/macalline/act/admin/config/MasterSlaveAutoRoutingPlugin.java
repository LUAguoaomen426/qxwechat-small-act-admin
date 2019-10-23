package com.red.star.macalline.act.admin.config;

import com.baomidou.dynamic.datasource.plugin.DbHealthIndicator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.config
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-23 13:39
 * @Version: 1.0
 */
@Intercepts({@Signature(
        type = org.apache.ibatis.executor.Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = org.apache.ibatis.executor.Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class MasterSlaveAutoRoutingPlugin implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterSlaveAutoRoutingPlugin.class);
    @Resource
    private DynamicDataSourceProperties properties;

    public MasterSlaveAutoRoutingPlugin() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        boolean empty = true;

        Object var5;
        try {
            empty = StringUtils.isEmpty(DynamicDataSourceContextHolder.peek());
            if (empty) {
                String dataSource = this.getDataSource(ms);
                LOGGER.trace("[mybatis]connected DataSource :{}", dataSource);
                DynamicDataSourceContextHolder.push(dataSource);
            }

            var5 = invocation.proceed();
        } finally {
            if (empty) {
                DynamicDataSourceContextHolder.clear();
            }

        }

        return var5;
    }

    public String getDataSource(MappedStatement mappedStatement) {
        String slave = "slave";
        if (this.properties.isHealth()) {
            boolean health = DbHealthIndicator.getDbHealth("slave");
            if (!health) {
                health = DbHealthIndicator.getDbHealth("master");
                if (health) {
                    slave = "master";
                }
            }
        }

        return SqlCommandType.SELECT == mappedStatement.getSqlCommandType() ? slave : "master";
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
