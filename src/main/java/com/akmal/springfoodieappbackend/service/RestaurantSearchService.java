package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.exception.InvalidQueryException;
import com.akmal.springfoodieappbackend.exception.MissingParametersException;
import com.akmal.springfoodieappbackend.mapper.RestaurantMapper;
import com.akmal.springfoodieappbackend.repository.elasticsearch.EsRestaurantRepository;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 27/01/2022 - 17:41
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RestaurantSearchService {
  private static final Set<String> VALID_SORT_OPTIONS =
      new HashSet<>(List.of("rating", "averageDeliveryTime", "deliveryCost"));
  private static final Set<String> VALID_DIRECTIONS = new HashSet<>(List.of("asc", "desc"));
  private static final Pattern SORT_PAIR_PATTERN = Pattern.compile("([a-z]+):[a-z]+");

  private final EsRestaurantRepository esRestaurantRepository;
  private final RestaurantMapper restaurantMapper;

  public Page<RestaurantDto> findAllByCountryAndCityRankByOpeningTimeWithinNow(
      String country, String city, int page, int size, List<String> sort) {

    if (!StringUtils.hasText(country)) {
      throw new MissingParametersException("Country parameter is required for the search");
    }
    if (!StringUtils.hasText(city)) {
      throw new MissingParametersException("City parameter is required for the search");
    }
    final var currentTime = LocalTime.now();
    final var sortingParams = this.extractSortingCriteria(sort);
    final var pageableSort = buildSort(sortingParams);
    final var pageable = PageRequest.of(page, size, pageableSort);

    return this.esRestaurantRepository
        .findAllByCountryAndCityRankByOpeningTimeWithin(country, city, currentTime, pageable)
        .map(hit -> this.restaurantMapper.esModelToDto(hit.getContent()));
  }

  private Map<String, String> extractSortingCriteria(List<String> sort) {
    Objects.requireNonNull(sort, "Sort is null, whereas it should have been an empty list");
    final Map<String, String> params = new HashMap<>();

    for (String sortingCriteria : sort) {
      if (!SORT_PAIR_PATTERN.matcher(sortingCriteria).matches()) {
        // violates the key:value pair constraint
        throw new InvalidQueryException(
            String.format(
                "Provided sorting criteria '%s' does not match key:value pattern",
                sortingCriteria));
      }

      final String[] args = sortingCriteria.split(":");

      if (!VALID_SORT_OPTIONS.contains(args[0])) {
        throw new InvalidQueryException(
            String.format("Provided sorting criteria '%s' is not supported", args[0]));
      }

      if (!VALID_DIRECTIONS.contains(args[1])) {
        throw new InvalidQueryException(
            String.format("Provided sorting direction '%s' is not supported", args[1]));
      }

      params.put(args[0], args[1]);
    }

    return params;
  }

  private Sort buildSort(Map<String, String> params) {
    final List<Order> orders = new LinkedList<>();

    for (Entry<String, String> entry : params.entrySet()) {
      if ("asc".equals(entry.getValue())) {
        orders.add(Order.asc(entry.getKey()));
      } else {
        orders.add(Order.desc(entry.getKey()));
      }
    }

    return Sort.by(orders);
  }
}
