package com.akmal.springfoodieappbackend.config;

import javax.validation.constraints.NotEmpty;
import lombok.Setter;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration class for the Elasticsearch beans.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 24/01/2022 - 19:30
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "es")
@Validated
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
  @NotEmpty(message = "At least one host is required")
  @Setter
  private String[] hosts;

  @Bean
  @Override
  public RestHighLevelClient elasticsearchClient() {
    final ClientConfiguration clientConfiguration =
        ClientConfiguration.builder().connectedTo(hosts).build();
    return RestClients.create(clientConfiguration).rest();
  }
}
