package com.howtodoinjava.app.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

  @Autowired
  private Environment env;

  /*@Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("app.datasource.driverClassName"));
    dataSource.setUrl(env.getProperty("app.datasource.url"));
    dataSource.setUsername(env.getProperty("app.datasource.username"));
    dataSource.setPassword(env.getProperty("app.datasource.password"));
    return dataSource;
  }*/
}
