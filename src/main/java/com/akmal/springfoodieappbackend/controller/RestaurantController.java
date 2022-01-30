package com.akmal.springfoodieappbackend.controller;

import static com.akmal.springfoodieappbackend.shared.http.ResponseEntityConverter.ok;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.service.RestaurantSearchService;
import com.akmal.springfoodieappbackend.service.RestaurantService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The RestController class containing methods of different HTTP request type handlers. The data
 * returned is strictly JSON. The class is using strictly constructor bean injection.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:42 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(RestaurantController.BASE_URL)
@RequiredArgsConstructor
public class RestaurantController {
  public static final String BASE_URL = "/api/restaurants";
  private final RestaurantService restaurantService;
  private final RestaurantSearchService restaurantSearchService;

  @GetMapping
  public Page<RestaurantDto> findAll(
      @RequestParam(defaultValue = "0", required = false) int page,
      @RequestParam(defaultValue = "10", required = false) int size,
      @RequestParam String city,
      @RequestParam String country,
      @RequestParam(value = "sort", required = false, defaultValue = "") List<String> sort) {
    return this.restaurantSearchService.findAllByCountryAndCityRankByOpeningTimeWithinNow(
        country, city, page, size, sort);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestaurantDto> findById(@PathVariable long id) {
    return ok(this.restaurantService.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RestaurantDto save(@RequestBody @Valid RestaurantDto restaurantDto) {
    return this.restaurantService.save(restaurantDto);
  }

  @PutMapping("/{id}")
  public RestaurantDto update(
      @PathVariable long id, @RequestBody @Valid RestaurantDto restaurantDto) {
    return this.restaurantService.update(id, restaurantDto);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable long id) {
    this.restaurantService.deleteById(id);
  }
}
