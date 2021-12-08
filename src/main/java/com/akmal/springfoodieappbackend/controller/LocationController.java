package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.tomtom.PlaceSearchResults;
import com.akmal.springfoodieappbackend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class is responsible for giving out search results for places based on the input.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 7:08 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(LocationController.BASE_URL)
@RequiredArgsConstructor
public class LocationController {
  public static final String BASE_URL = "/api/locations";
  private final LocationService locationService;

  @GetMapping("/search")
  public PlaceSearchResults searchPlaces(
      @RequestParam String query, @RequestParam(defaultValue = "10") int limit) {
    return this.locationService.search(query, limit);
  }
}
