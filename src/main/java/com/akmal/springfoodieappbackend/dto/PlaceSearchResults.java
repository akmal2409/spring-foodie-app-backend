package com.akmal.springfoodieappbackend.dto;

import java.util.List;

/**
 * The class represents the response object from the TomTomAPI.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 6:58 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record PlaceSearchResults(int numResults,
                                 int offset,
                                 int totalResults,
                                 List<PlaceSearchResult> results) {
  public static PlaceSearchResults empty() {
    return new PlaceSearchResults(0, 0, 0, List.of());
  }
}
