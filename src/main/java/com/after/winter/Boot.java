package com.after.winter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

public class Boot {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    //(LocalEntityManagerFactoryBean)context.getBean("entityManagerFactory");
  }

}
