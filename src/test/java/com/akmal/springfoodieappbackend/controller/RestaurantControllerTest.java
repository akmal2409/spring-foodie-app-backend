package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.config.MessageSourceConfig;
import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.errorHandling.GlobalRestExceptionHandler;
import com.akmal.springfoodieappbackend.service.RestaurantService;
import com.akmal.springfoodieappbackend.shared.TestPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
  private MockMvc mockMvc;

  JacksonTester<TestPage<RestaurantDto>> jacksonTester;

  @BeforeEach
  void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  @DisplayName("Test findAll() should return an empty list and HTTP OK")
  void testFindSucceedsEmptyResponse() throws Exception {
    given(restaurantService.findAll(anyInt(), anyInt())).willReturn(new PageImpl<>(List.of(), Pageable.ofSize(1), 0));

    MockHttpServletResponse response = this.mockMvc.perform(get(RestaurantController.BASE_URL)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

    TestPage<RestaurantDto> actualPage = this.jacksonTester.parseObject(response.getContentAsString());

    assertThat(actualPage.getContent()).isEmpty();
  }

  @Test
  @DisplayName("Test findAll() should return a list of restaurant objects of size 1")
  void testFindAllSucceeds() throws Exception {
    final var expectedRestaurant = new RestaurantDto(1L, "Test name", null,
            null, 0, BigDecimal.valueOf(0),0,0.0,null,null,null);
    final var expectedPage = new TestPage<>(List.of(expectedRestaurant), PageRequest.ofSize(1), 1);

    given(restaurantService.findAll(anyInt(), anyInt())).willReturn(expectedPage);

    MockHttpServletResponse response = this.mockMvc.perform(get(RestaurantController.BASE_URL)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
    TestPage<RestaurantDto> actualPage = this.jacksonTester.parseObject(response.getContentAsString());

    assertThat(actualPage.getContent()).hasSize(1);
    assertThat(actualPage.getContent()).extracting(RestaurantDto::id).contains(1L);
    assertThat(actualPage.getContent()).extracting(RestaurantDto::name).contains("Test name");
  }

  @Test
  void findById() {
  }

  @Test
  void save() {
  }

  @Test
  void update() {
  }

  @Test
  void deleteById() {
  }

  @Test
  void deleteAll() {
  }
}
