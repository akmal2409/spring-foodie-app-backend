package com.akmal.springfoodieappbackend.config;

import com.akmal.springfoodieappbackend.shared.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * The class defines the configuration for the WEB layer as well as beans that are needed.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 7:27 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Slf4j
@Configuration
public class WebConfig {

  @Bean
  public ClientHttpRequestInterceptor requestInterceptor() {
    return (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
      log.info(
          "type=externalRequest requestStatus=dispatched method={} url={} size={} time={}",
          request.getMethod(),
          request.getURI(),
          body.length,
          Instant.now());
      final var stopWatch = StopWatch.start();
      ClientHttpResponse response = execution.execute(request, body);
      stopWatch.stop();
      log.info(
          "type=externalRequest requestStatus=complete method={} url={} size={} request_time={}ms httpStatus={}",
          request.getMethod(),
          request.getURI(),
          body.length,
          stopWatch.toMili(),
          response.getStatusCode());
      return response;
    };
  }

  @Bean
  public RestTemplate restTemplate() {
    final var template = new RestTemplate();

    List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }

    interceptors.add(requestInterceptor());
    template.setInterceptors(interceptors);
    return template;
  }
}
