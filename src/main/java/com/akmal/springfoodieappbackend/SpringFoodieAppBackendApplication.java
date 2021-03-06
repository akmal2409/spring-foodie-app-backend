package com.akmal.springfoodieappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringFoodieAppBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringFoodieAppBackendApplication.class, args);
  }
}
