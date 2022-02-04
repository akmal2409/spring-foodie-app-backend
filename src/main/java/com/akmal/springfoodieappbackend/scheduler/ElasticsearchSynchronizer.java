package com.akmal.springfoodieappbackend.scheduler;

import com.akmal.springfoodieappbackend.mapper.RestaurantMapper;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.model.elasticsearch.ESRestaurant;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import com.akmal.springfoodieappbackend.repository.elasticsearch.EsRestaurantRepository;
import com.akmal.springfoodieappbackend.shared.StopWatch;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class is responsible for synchronizing the data between primary SQL datastore and the
 * Elasticsearch cluster.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 27/01/2022 - 17:05
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticsearchSynchronizer {
  private final TransactionRunner transactionRunner;
  private final EsRestaurantRepository esRestaurantRepository;
  private final RestaurantRepository restaurantRepository;
  private final RestaurantMapper restaurantMapper;

  /**
   * The method is responsible for synchronizing the data between SQL and Elasticsearch and runs
   * every 10 minutes as specified in the CRON expression. In order to avoid looking at all data, we
   * will select only the ones whose updated timestamps were after specified intervals.
   */
  @Scheduled(cron = "0 */10 * * * *")
  @Transactional
  public void sync() {
    final StopWatch stopWatch = StopWatch.start();
    log.info(
        "{\"type\": \"database\", \"timestamp\": \"{}\", \"level\": \"INFO\", \"component\": "
            + "\"com.akmal.springfoodieappbackend.scheduler.ElasticsearchSynchronizer\", \"operation\": "
            + "\"database sync\", \"status\": \"pending\", \"message\": \"Starting synchronization of "
            + "SQL datastore and Elasticsearch\"}",
        LocalDateTime.now());
    this.transactionRunner.runInTransaction(this::syncRestaurants);
    stopWatch.stop();
    log.info(
        "{\"type\": \"database\", \"timestamp\": \"{}\", \"level\": \"INFO\", \"component\": "
            + "\"com.akmal.springfoodieappbackend.scheduler.ElasticsearchSynchronizer\", \"operation\": "
            + "\"database sync\", \"status\": \"finished\", \"message\": \"Finished synchronizing SQL "
            + "datastore with Elasticsearch\"}, took\": \"{}ms\"",
        LocalDateTime.now(),
        stopWatch.toMili());
  }

  private void syncRestaurants() {
    List<Restaurant> restaurants = null;
    long count = 0L;

    if (this.esRestaurantRepository.count(EsRestaurantRepository.RESTAURANTS_INDEX) == 0) {
      restaurants = this.restaurantRepository.findAll();
      count = this.restaurantRepository.count();
    } else {
      restaurants =
          this.restaurantRepository.findAllByUpdatedOnAfter(LocalDateTime.now().minusMinutes(10));
      count = restaurants.size();
    }

    if (count > 0) {
      final List<ESRestaurant> mappedRestaurants =
          restaurants.stream().map(this.restaurantMapper::toESModel).toList();
      this.esRestaurantRepository.bulkIndex(
          mappedRestaurants, EsRestaurantRepository.RESTAURANTS_INDEX);
    } else {
      log.info(
          "{\"type\": \"database\", \"timestamp\": \"{}\", \"level\": \"INFO\", \"component\": "
              + "\"com.akmal.springfoodieappbackend.scheduler.ElasticsearchSynchronizer\", \"operation\": "
              + "\"database sync\", \"status\": \"complete\", \"message\": \"No objects to sync\"",
          LocalDateTime.now());
    }
  }
}
