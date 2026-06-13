package id.sis.service.accounting.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import id.sis.service.accounting.properties.SISSourceDataSourceProperties;

@Configuration
public class SISSourceConfig {

    @Bean(name = "sourceDataSource")
    public DataSource sourceDataSource(SISSourceDataSourceProperties prop) {

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(prop.getUrl());
        ds.setUsername(prop.getUsername());
        ds.setPassword(prop.getPassword());

        ds.setMaximumPoolSize(prop.getMaxPoolSize());
        ds.setMinimumIdle(prop.getMinIdle());
        ds.setConnectionTimeout(prop.getConnectionTimeout());
        ds.setIdleTimeout(prop.getIdleTimeout());
        ds.setMaxLifetime(prop.getMaxLifetime());

        ds.setAutoCommit(false); // PENTING untuk @Transactional

        return ds;
    }

    @Bean(name = "jdbcTemplateSource")
    public JdbcTemplate jdbcTemplateSource(
            @Qualifier("sourceDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "sourceTxManager")
    @Primary
    public PlatformTransactionManager sourceTxManager(
            @Qualifier("sourceDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}