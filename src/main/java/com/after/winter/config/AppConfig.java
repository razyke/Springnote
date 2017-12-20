package com.after.winter.config;

import java.sql.SQLException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = {"com.after.winter.services",
    "com.after.winter.model",
    "com.after.winter.repository",
    "com.after.winter.aop"})
@EnableJpaRepositories("com.after.winter.repository")
@EnableTransactionManagement

@EnableAspectJAutoProxy
//@PropertySource("classpath:db.properties")
public class AppConfig {

/*//For property variable
  private Environment env;
  @Autowired
  AppConfig(Environment env) {
    this.env = env;
  }
  //THIS IS datasource FROM property file
  @Bean
  public DataSource dataSource() throws SQLException {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("driverClassName"));
    dataSource.setUrl(env.getProperty("url"));
    dataSource.setUsername("root");
    dataSource.setPassword("1234");
    return dataSource;
  }*/

  @Bean
  public DataSource dataSource() throws SQLException {
    return new EmbeddedDatabaseBuilder().setName("test").
        setType(EmbeddedDatabaseType.H2)
        .addScript("generatedb.sql")
        .addScript("insert.sql")
        .build();
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
    bean.setDatabase(Database.H2);
    bean.setGenerateDdl(true);
    return bean;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setDataSource(dataSource);
    bean.setJpaVendorAdapter(jpaVendorAdapter);
    bean.setPackagesToScan("com.after.winter");
    return bean;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }


}