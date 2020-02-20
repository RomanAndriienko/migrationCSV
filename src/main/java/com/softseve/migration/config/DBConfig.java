package com.softseve.migration.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DBConfig {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
            .setType(EmbeddedDatabaseType.HSQL)
            .setName("patient")
            .addScript("db/sql/create_patient_result.sql")
            .addScript("db/sql/create_source.sql")
            .build();
    }
}
