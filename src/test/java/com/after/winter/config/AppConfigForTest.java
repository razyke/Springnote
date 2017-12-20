package com.after.winter.config;

import java.sql.SQLException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import springfox.documentation.spring.web.plugins.Docket;

public class AppConfigForTest extends AppConfig {

  @Bean
  @Override
  public DataSource dataSource() throws SQLException {
    return new EmbeddedDatabaseBuilder().setName("test2").
        setType(EmbeddedDatabaseType.H2).build();
  }

}
