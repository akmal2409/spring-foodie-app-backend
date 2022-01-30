package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.repository.elasticsearch.EsRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
  private final EsRestaurantRepository esRestaurantRepository;
}
