package com.akmal.springfoodieappbackend.repository.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

import com.akmal.springfoodieappbackend.model.OpeningTime;
import com.akmal.springfoodieappbackend.model.elasticsearch.ESRestaurant;
import com.akmal.springfoodieappbackend.shared.StopWatch;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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

  public SearchPage<ESRestaurant> findAllByCountryAndCityRankByOpeningTimeWithin(
      String country, String city, LocalTime time, Pageable pageable) {

    Query query =
        new NativeSearchQueryBuilder()
            .withQuery(
                QueryBuilders.boolQuery()
                    .should(queryRankingByOpeningTime(time))
                    .must(QueryBuilders.termQuery("address.country", country))
                    .must(QueryBuilders.termQuery("address.city", city)))
            .withPageable(pageable)
            .build();

    final SearchHits<ESRestaurant> hits =
        this.esOps.search(query, ESRestaurant.class, IndexCoordinates.of(RESTAURANTS_INDEX));

    return SearchHitSupport.searchPageFor(hits, query.getPageable());
  }

  public long count(String index) {
    return this.esOps.count(Query.findAll(), IndexCoordinates.of(index));
  }

  private static NestedQueryBuilder queryRankingByOpeningTime(LocalTime time) {
    return nestedQuery(
        "openingTimes",
        QueryBuilders.boolQuery()
            .must(
                QueryBuilders.rangeQuery("openingTimes.openFrom")
                    .lte(time.format(OpeningTime.formatter))
                    .format("hour_minute"))
            .must(
                QueryBuilders.rangeQuery("openingTimes.openTill")
                    .gte(time.format(OpeningTime.formatter))
                    .format("hour_minute"))
            .must(QueryBuilders.termQuery("openingTimes.day", 7)),
        ScoreMode.Max);
  }
}
