package com.akmal.springfoodieappbackend.config;

import com.akmal.springfoodieappbackend.controller.RestaurantController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests ->
                    authorizeRequests
                            .antMatchers(HttpMethod.GET, RestaurantController.BASE_URL, RestaurantController.BASE_URL + "/{id}")
                            .permitAll()
                            .antMatchers(HttpMethod.POST, RestaurantController.BASE_URL).hasAnyRole("RESTAURANT", "ADMIN")
                            .antMatchers(HttpMethod.PUT, RestaurantController.BASE_URL + "/{id}").hasAnyRole("RESTAURANT", "ADMIN")
                            .anyRequest().authenticated())
            .oauth2ResourceServer().jwt();
  }
}
