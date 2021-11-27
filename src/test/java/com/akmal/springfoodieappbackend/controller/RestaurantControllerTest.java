package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.config.MessageSourceConfig;
import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.errorHandling.GlobalRestExceptionHandler;
import com.akmal.springfoodieappbackend.service.RestaurantService;
import com.akmal.springfoodieappbackend.shared.TestPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 14/11/2021 - 12:44 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */

@WebMvcTest(RestaurantController.class)
@Import({GlobalRestExceptionHandler.class, MessageSourceConfig.class})
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
class RestaurantControllerTest {

  @MockBean
  RestaurantService restaurantService;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;

  private static RestaurantDto emptyRestaurantWithNameAndId() {
    return new RestaurantDto(1L, "Test name", null,
            null, 0, BigDecimal.valueOf(0), 0, 0.0, null, null, null, null, null);
  }

  private static Page<RestaurantDto> pageOf(List<RestaurantDto> dtos) {
    return new TestPage<>(dtos, PageRequest.ofSize(Math.max(dtos.size(), 1)), Math.max(dtos.size(), 1));
  }

  @Test
  @DisplayName("Test findAll() should return an empty list and HTTP OK")
  void testFindSucceedsEmptyResponse() throws Exception {
    given(restaurantService.findAll(anyInt(), anyInt())).willReturn(pageOf(List.of()));

    MockHttpServletResponse response = this.mockMvc.perform(get(RestaurantController.BASE_URL)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

    TestPage<RestaurantDto> actualPage = this.objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
    });

    assertThat(actualPage.getContent()).isEmpty();
  }

  @Test
  @DisplayName("Test findAll() should return a list of restaurant objects of size 1")
  void testFindAllSucceeds() throws Exception {
    final var expectedRestaurant = emptyRestaurantWithNameAndId();
    final var expectedPage = pageOf(List.of(expectedRestaurant));
    given(restaurantService.findAll(anyInt(), anyInt())).willReturn(expectedPage);

    MockHttpServletResponse response = this.mockMvc.perform(get(RestaurantController.BASE_URL)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
    TestPage<RestaurantDto> actualPage = this.objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
    });

    assertThat(actualPage.getContent()).hasSize(1);
    assertThat(actualPage.getContent()).containsOnly(expectedRestaurant);
  }

  @Test
  @DisplayName("Test findById() succeeds when correct id is given")
  void testFindByIdSucceeds() throws Exception {
    final var expectedRestaurantDto = emptyRestaurantWithNameAndId();

    given(restaurantService.findById(anyLong())).willReturn(expectedRestaurantDto);

    MockHttpServletResponse response = this.mockMvc.perform(get(RestaurantController.BASE_URL + "/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

    final var actualRestaurantDto = this.objectMapper.readValue(response.getContentAsString(), RestaurantDto.class);

    assertThat(actualRestaurantDto).usingRecursiveComparison()
            .isEqualTo(expectedRestaurantDto);
  }

  @Test
  @DisplayName("Test findById() succeeds when entity has not been found, returns null. Expected HTTP 204")
  void testFindByIdSucceedsEmptyResult() throws Exception {
    given(restaurantService.findById(anyLong())).willReturn(null);

    this.mockMvc.perform(get(RestaurantController.BASE_URL + "/2")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
  }

  @Test
  @DisplayName("Test update() will succeed and return DTO")
  void testUpdateSucceeds() throws Exception {
    final var expectedDto = emptyRestaurantWithNameAndId();
    given(this.restaurantService.update(anyLong(), any(RestaurantDto.class))).willReturn(expectedDto);

    MockHttpServletResponse response = this.mockMvc.perform(put(RestaurantController.BASE_URL + "/1")
                    .content(Objects.requireNonNull(asJson(expectedDto)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

    final var actualDto = this.objectMapper.readValue(response.getContentAsString(), RestaurantDto.class);

    assertThat(actualDto)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expectedDto);
  }

  @Test
  @DisplayName("Test save() will succeed and return DTO")
  void testSaveSucceeds() throws Exception {
    final var expectedDto = emptyRestaurantWithNameAndId();
    given(this.restaurantService.save(any(RestaurantDto.class))).willReturn(expectedDto);

    MockHttpServletResponse response = this.mockMvc.perform(post(RestaurantController.BASE_URL)
                    .content(Objects.requireNonNull(asJson(expectedDto)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse();

    final var actualDto = this.objectMapper.readValue(response.getContentAsString(), RestaurantDto.class);

    assertThat(actualDto)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expectedDto);
  }

  @Test
  @DisplayName("Test deleteById() Will succeed")
  void deleteById() throws Exception {
    this.mockMvc.perform(delete(RestaurantController.BASE_URL + "/2")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
  }

  @Test
  @DisplayName("Test deleteAll() will succeed")
  void deleteAll() throws Exception {
    this.mockMvc.perform(delete(RestaurantController.BASE_URL)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
  }

  private <T> String asJson(T obj) {
    try {
      return this.objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
