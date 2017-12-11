package com.after.winter.config;

import java.sql.SQLException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
//@PropertySource("classpath:db.properties")
@ComponentScan("com.after.winter")
@EnableJpaRepositories("com.after.winter.repository")
public class AppConfig {


/*
//For property variable
  @Autowired
  Environment env;*/

  //THIS IS datasource FROM property file
 /* @Bean
  public DataSource dataSource() throws SQLException {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("driverClassName"));
    dataSource.setUrl(env.getProperty("url"));
    dataSource.setUsername("username");
    dataSource.setPassword("password");
    return dataSource;
  }*/

  @Bean
  public DataSource dataSource() throws SQLException {
    return new EmbeddedDatabaseBuilder().setName("test").
        setType(EmbeddedDatabaseType.H2).build();
  }


  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    try {
      em.setDataSource(dataSource());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    em.setPackagesToScan("com.after.winter");
    em.setJpaVendorAdapter(jpaVendorAdapter());
    return em;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
    bean.setDatabase(Database.H2);
    bean.setGenerateDdl(true);
    return bean;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
