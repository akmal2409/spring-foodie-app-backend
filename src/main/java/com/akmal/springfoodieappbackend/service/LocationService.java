package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.tomtom.PlaceSearchResults;
import com.akmal.springfoodieappbackend.dto.tomtom.TomTomErrorResponse;
import com.akmal.springfoodieappbackend.exception.ExternalCallException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

/**
 * The class is responsible for communicating with TomTom API, to search for places and calculating
 * the distances between the locations.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 7:11 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
  private final RestTemplate restTemplate;

  @Value("${tomtom.api.key}")
  private String tomtomApiKey;

  @Value("${tomtom.api.url}")
  private String tomtomApiUrl;

  /**
   * Method is responsible for retrieving the search results from the TomTom search API that
   * performs fuzzy search.
   *
   * @param query of a place
   * @param limit number of results
   * @return {@link PlaceSearchResults} instance object.
   * @throws ExternalCallException if the call to TomTomAPI fails
   * @throws JsonProcessingException if the jackson fails to interpret the error body
   */
  public PlaceSearchResults search(String query, int limit) {
    if (!StringUtils.hasText(query)) {
      return PlaceSearchResults.empty();
    }
    try {
      ResponseEntity<PlaceSearchResults> response =
          this.restTemplate.getForEntity(
              this.tomtomApiUrl
                  + "/search/2/search/{encodedQuery}.json?typeahead=true&limit={limit}&key={tomtomApiKey}",
              PlaceSearchResults.class,
              query,
              limit,
              this.tomtomApiKey);
      return response.getBody();
    } catch (HttpStatusCodeException e) {
      final var mapper = new ObjectMapper();

      try {
        final var tomtomApiError =
            mapper.readValue(e.getResponseBodyAsString(), TomTomErrorResponse.class);

        log.error(
            "time={} type=externalCall requestStatus=failed method=GET url={} httpStatus={} error={}, errorCode={} debugMessage={}",
            Instant.now(),
            String.format(
                "%s/search/2/search/%s.json?typeahead=true&limit=%d&key=<apiKey>",
                this.tomtomApiUrl, query, limit),
            e.getRawStatusCode(),
            tomtomApiError.errorText(),
            tomtomApiError.detailedError().code(),
            tomtomApiError.detailedError().message());

      } catch (JsonProcessingException ex) {
        log.error(
            "type=serialization status=failed error=Object Mapper has failed when converting TomTom API Error response debugMessage={}",
            ex.getMessage());
      }
      throw new ExternalCallException(
          String.format(
              "External call to TomTom API has failed. The API responded with HTTP %d. Message: %s",
              e.getRawStatusCode(), e.getMessage()));
    }
  }
}
