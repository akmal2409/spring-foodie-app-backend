package com.akmal.springfoodieappbackend.config;

import com.akmal.springfoodieappbackend.controller.*;
import com.akmal.springfoodieappbackend.filter.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!test & !integration-test")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  public static final String ROLE_USER = "USER";
  public static final String ROLE_RESTAURANT = "RESTAURANT";
  public static final String ROLE_ADMIN = "ADMIN";
  private final CorsFilter corsFilter;

  public SecurityConfig(CorsFilter corsFilter) {
    this.corsFilter = corsFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(this.corsFilter, SessionManagementFilter.class)
        .authorizeRequests(
            authorizeRequests ->
                authorizeRequests
                    .antMatchers(
                        HttpMethod.GET,
                        RestaurantController.BASE_URL,
                        RestaurantController.BASE_URL + "/{id}")
                    .permitAll()
                    .antMatchers(LocationController.BASE_URL + "/search")
                    .permitAll()
                    .antMatchers(
                        HttpMethod.GET,
                        CategoryController.BASE_URL,
                        CategoryController.BASE_URL + "/{id}")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, RestaurantController.BASE_URL)
                    .hasAnyRole(ROLE_RESTAURANT, SecurityConfig.ROLE_ADMIN)
                    .antMatchers(HttpMethod.PUT, RestaurantController.BASE_URL + "/{id}")
                    .hasAnyRole(ROLE_RESTAURANT, SecurityConfig.ROLE_ADMIN)
                    .antMatchers(HttpMethod.DELETE, RestaurantController.BASE_URL + "/{id}")
                    .hasAnyRole(ROLE_RESTAURANT, SecurityConfig.ROLE_ADMIN)
                    .antMatchers(HttpMethod.DELETE, RestaurantController.BASE_URL)
                    .hasRole(SecurityConfig.ROLE_ADMIN)
                    .antMatchers(CategoryController.BASE_URL)
                    .hasRole(SecurityConfig.ROLE_ADMIN)
                    .antMatchers(
                        HttpMethod.POST,
                        MenuItemController.BASE_URL + "/menu-items/*",
                        MenuItemController.BASE_URL + "/menus/{menuId}/menu-items/*")
                    .hasAnyRole(SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_RESTAURANT)
                    .antMatchers(
                        HttpMethod.PUT,
                        MenuItemController.BASE_URL + "/menu-items/*",
                        MenuItemController.BASE_URL + "/menus/{menuId}/menu-items/*")
                    .hasAnyRole(SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_RESTAURANT)
                    .antMatchers(
                        HttpMethod.DELETE,
                        MenuItemController.BASE_URL + "/menu-items/*",
                        MenuItemController.BASE_URL + "/menus/{menuId}/menu-items/*")
                    .hasAnyRole(SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_RESTAURANT)
                    .antMatchers(HttpMethod.POST, MenuController.BASE_URL + "/menus/*")
                    .hasAnyRole(SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_RESTAURANT)
                    .antMatchers(HttpMethod.PUT, MenuController.BASE_URL + "/menus/*")
                    .hasAnyRole(SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_RESTAURANT)
                    .antMatchers(HttpMethod.DELETE, MenuController.BASE_URL + "/menus/*")
                    .hasAnyRole(SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_RESTAURANT)
                    .anyRequest()
                    .authenticated())
        .csrf()
        .disable()
        .oauth2ResourceServer()
        .jwt();
  }
}
