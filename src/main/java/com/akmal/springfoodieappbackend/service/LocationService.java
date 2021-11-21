package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.PlaceSearchResults;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.UriUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

/**
 * The class is responsible for communicating with TomTom API, to search for places
 * and calculating the distances between the locations.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 7:11 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class LocationService {
  @Value("${tomtom.api.key}")
  private String tomtomApiKey;
  @Value("${tomtom.api.url}")
  private String tomtomApiUrl;
  private final RestTemplate restTemplate;

  /**
   * Method is responsible for retrieving the search results from the TomTom search API
   * that performs fuzzy search.
   *
   * @param query of a place
   * @param limit number of results
   * @return {@link PlaceSearchResults} instance object.
   */
  public PlaceSearchResults search(String query, int limit) {
    if (!StringUtils.hasText(query)) {
      return PlaceSearchResults.empty();
    }

    ResponseEntity<PlaceSearchResults> response = this.restTemplate
            .getForEntity(this.tomtomApiUrl + "/search/2/search/{encodedQuery}.json?typeahead=true&limit={limit}&key={tomtomApiKey}",
                    PlaceSearchResults.class,
                    query,
                    limit,
                    this.tomtomApiKey);

    return response.getBody();
  }
}
