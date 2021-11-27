package com.akmal.springfoodieappbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class represents the configuration for unit tests to run.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 15/11/2021 - 5:09 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Configuration
@Profile("test")
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests ->
                    authorizeRequests
                            .anyRequest()
                            .permitAll())
            .csrf().disable();
  }
}
