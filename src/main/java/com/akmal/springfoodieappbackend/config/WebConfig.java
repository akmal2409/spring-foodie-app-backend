package com.akmal.springfoodieappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The class defines the configuration for the WEB layer as well as beans that are needed.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 7:27 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Configuration
public class WebConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
