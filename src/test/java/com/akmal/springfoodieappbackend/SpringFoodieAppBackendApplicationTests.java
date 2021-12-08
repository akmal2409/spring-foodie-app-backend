package com.akmal.springfoodieappbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"test", "aws-disabled"})
class SpringFoodieAppBackendApplicationTests {

  @Test
  void contextLoads() {}
}
