package com.after.winter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {AppConfigForTest.class, AppInit.class},
        basePackages = {"com.after.winter.controllers"})
public class WebConfigForTests extends WebMvcConfigurerAdapter {
}
