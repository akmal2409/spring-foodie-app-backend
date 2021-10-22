package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The RestController class containing methods of different HTTP request type handlers.
 * The data returned is strictly JSON/
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:42 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(RestaurantController.BASE_URL)
public class RestaurantController {
  public static final String BASE_URL = "/api/restaurants";

  @GetMapping
  public Page<RestaurantDto> findAll() {
    return Page.empty();
  }
}
