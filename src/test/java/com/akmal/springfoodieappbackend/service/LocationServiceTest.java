package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.tomtom.PlaceSearchResult;
import com.akmal.springfoodieappbackend.dto.tomtom.PlaceSearchResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 5:49 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private LocationService locationService;

  @Test
  @DisplayName("Test search() will succeed when null query is provided and the result will be empty PlaceSearchResults")
  void testSearchSucceedEmptyQuery() {
    final var expectedResult = PlaceSearchResults.empty();

    final var actualResult = this.locationService.search(null, 10);

    assertThat(actualResult)
            .usingRecursiveComparison()
            .isEqualTo(expectedResult);
  }

  @Test
  @DisplayName("Test search() will succeed when valid query is passed")
  void testSearchSucceed() {
    final var expectedResult = new PlaceSearchResults(10, 10, 10, List.of(new PlaceSearchResult(
            "building", "3423", null, null)));

    when(restTemplate.getForEntity(anyString(), any(), any(), any(), any())).thenReturn(ResponseEntity.ok(expectedResult));

    final var actualResult = this.locationService.search("test", 10);

    assertThat(actualResult)
            .usingRecursiveComparison()
            .isEqualTo(expectedResult);
  }
}
