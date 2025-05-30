package ru.hogwarts.school;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws Exception {
        DataSource dataSource = DataSourceBuilder.create()
                .url(dbUrl)
                .username(username)
                .password(password)
                .build();
        dataSource.getConnection().setAutoCommit(false);
        return dataSource;
    }

}
