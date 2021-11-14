package com.akmal.springfoodieappbackend.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Properties;

/**
 * <h1>MessageSourceConfig</h1>
 * Is a configuration class that keeps the beans needed for reading and retrieving the
 * message string representations from the properties file.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 14/11/2021 - 1:03 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Configuration
public class MessageSourceConfig {

  @Bean(name = "errorMessageProps")
  public Properties errorMessageProps() {
    final var propertiesFactory = new YamlPropertiesFactoryBean();
    propertiesFactory.setResources(new ClassPathResource("messages" + File.separator + "error-messages.yml"));
    return propertiesFactory.getObject();
  }
}
