package com.wt.seckilling.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author qiyu
 * @date 2019/2/6 21:36
 * @description
 */
@Configuration
@Slf4j
public class SeckillingDataSourceConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "seckilling.datasource")
    @Primary
    public DataSource getDataSource() throws SQLException {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "seckillingTransactionManager")
    public DataSourceTransactionManager transactitonManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
