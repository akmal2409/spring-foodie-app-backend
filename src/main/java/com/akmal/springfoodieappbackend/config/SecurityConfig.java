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
  public static final String ROLE_USER = "USER";
  public static final String ROLE_RESTAURANT = "RESTAURANT";
  public static final String ROLE_ADMIN = "ADMIN";


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests ->
                    authorizeRequests
                            .antMatchers(HttpMethod.GET, RestaurantController.BASE_URL, RestaurantController.BASE_URL + "/{id}")
                            .permitAll()
                            .antMatchers(HttpMethod.POST, RestaurantController.BASE_URL).hasAnyRole(ROLE_RESTAURANT, SecurityConfig.ROLE_ADMIN)
                            .antMatchers(HttpMethod.PUT, RestaurantController.BASE_URL + "/{id}").hasAnyRole(ROLE_RESTAURANT, SecurityConfig.ROLE_ADMIN)
                            .antMatchers(HttpMethod.DELETE, RestaurantController.BASE_URL + "/{id}").hasAnyRole(ROLE_RESTAURANT, SecurityConfig.ROLE_ADMIN)
                            .antMatchers(HttpMethod.DELETE, RestaurantController.BASE_URL).hasRole(SecurityConfig.ROLE_ADMIN)
                            .anyRequest().authenticated())
            .oauth2ResourceServer().jwt();
  }
}
