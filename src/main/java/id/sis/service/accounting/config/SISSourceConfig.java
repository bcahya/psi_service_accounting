package id.sis.service.accounting.config;

import javax.sql.DataSource;

import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.atomikos.jdbc.AtomikosDataSourceBean;

import id.sis.service.accounting.properties.SISSourceDataSourceProperties;

@Configuration
@EnableConfigurationProperties(SISSourceDataSourceProperties.class)
public class SISSourceConfig {
    @Autowired
    SISSourceDataSourceProperties sisSourceDataSourceProperties;

    @Bean(name = "sisSourceDataSource")
    public DataSource sisDataSource() {
    	PGXADataSource pgXADataSource = new PGXADataSource();
		 pgXADataSource.setUrl(sisSourceDataSourceProperties.getUrl());
		 pgXADataSource.setPassword(sisSourceDataSourceProperties.getPassword());
		 pgXADataSource.setUser(sisSourceDataSourceProperties.getUsername());
	
		 AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		 xaDataSource.setXaDataSource(pgXADataSource);
		 xaDataSource.setUniqueResourceName("xads1");
		 xaDataSource.setMaxPoolSize(Integer.valueOf(sisSourceDataSourceProperties.getMaxpool()));
		 return xaDataSource;
    }

    @Bean(name = "jdbcTemplateCdcSource")
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "sisSourceDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
