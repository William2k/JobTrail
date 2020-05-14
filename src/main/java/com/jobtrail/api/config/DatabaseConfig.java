package com.jobtrail.api.config;

import com.jobtrail.api.core.CustomJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {
    private final ConfigProperties config;

    @Autowired
    public DatabaseConfig(ConfigProperties config) {
        this.config = config;
    }

    @Bean
    public CustomJdbc getJdbc() {
        String url = config.getConfigValue("datasource.url");
        String driverClassName = config.getConfigValue("datasource.driverClassName");
        String username = config.getConfigValue("datasource.username");
        String password = config.getConfigValue("datasource.password");
        String catalog = config.getConfigValue("datasource.catalog");
        String schema = config.getConfigValue("datasource.schema");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setSchema(schema);
        dataSource.setCatalog(catalog);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return new CustomJdbc(dataSource);
    }
}
