package com.akmal.springfoodieappbackend.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

/**
 * The abstract class represents a class that can be inherited by others in order to perform
 * integration tests with the use of <strong>Testcontainers</strong> library that will create a
 * docker container where Mysql instance will be running. We must have an abstract class in order to
 * prevent Testcontainers spinning up new docker instances for each test.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 02/12/2021 - 7:01 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseContainerMysqlTest {

  public static MySQLContainer mySQLContainer =
      new MySQLContainer("mysql:latest")
          .withDatabaseName("foodie-it-db")
          .withUsername("root")
          .withPassword("root");

  static {
    mySQLContainer.start();
  }

  @DynamicPropertySource
  static void dbProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
  }
}
