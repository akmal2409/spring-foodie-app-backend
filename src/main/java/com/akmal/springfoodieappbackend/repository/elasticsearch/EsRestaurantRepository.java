package com.akmal.springfoodieappbackend.repository.elasticsearch;

import com.akmal.springfoodieappbackend.model.elasticsearch.ESRestaurant;
import com.akmal.springfoodieappbackend.shared.StopWatch;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 27/01/2022 - 17:47
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EsRestaurantRepository {
  private final ElasticsearchOperations esOps;
  public static final String RESTAURANTS_INDEX = "restaurants";

  public void bulkIndex(List<ESRestaurant> restaurants, String index) {
    log.info(
        "{\"type\": \"database\", \"timestamp\": \"{}\", \"level\": \"INFO\", \"component\": "
            + "\"com.akmal.springfoodieappbackend.repository.elasticsearch.EsRestaurantRepository\", "
            + "\"operation\": \"bulk index\", \"status\": \"pending\", \"number\": \"{}\", \"message\": "
            + "\"Starting bulk indexing documents to Elasticsearch index: {}\"}",
        LocalDateTime.now(),
        restaurants.size(),
        RESTAURANTS_INDEX);
    final StopWatch stopWatch = StopWatch.start();
    final List<IndexQuery> indexQueries = new LinkedList<>();

    for (ESRestaurant restaurant : restaurants) {
      final var indexQuery =
          new IndexQueryBuilder()
              .withId(restaurant.getId().toString())
              .withObject(restaurant)
              .build();

      indexQueries.add(indexQuery);
    }

    this.esOps.bulkIndex(indexQueries, IndexCoordinates.of(index));

    stopWatch.stop();
    log.info(
        "{\"type\": \"database\", \"timestamp\": \"{}\", \"level\": \"INFO\", \"component\": "
            + "\"com.akmal.springfoodieappbackend.repository.elasticsearch.EsRestaurantRepository\", "
            + "\"operation\": \"bulk index\", \"status\": \"finished\", \"message\": "
            + "\"Finished indexing to Elasticsearch index: {}\", \"took\": \"{}ms\"}",
        LocalDateTime.now(),
        index,
        stopWatch.toMili());
  }

  public long count(String index) {
    return this.esOps.count(Query.findAll(), IndexCoordinates.of(index));
  }
}
