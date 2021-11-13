package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.exception.InsufficientRightsException;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.RestaurantMapper;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.model.User;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 13/11/2021 - 7:35 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
  @Mock
  private RestaurantRepository restaurantRepository;
  @Mock
  private RestaurantMapper restaurantMapper;
  @Mock
  private UserService userService;

  @InjectMocks
  private RestaurantService restaurantService;

  @Captor
  ArgumentCaptor<Restaurant> restaurantArgumentCaptor;

  @Captor
  ArgumentCaptor<Long> idArgumentCaptor;

  @Test
  @DisplayName("Test findAll() method returns correct result")
  void testFindAllSucceeds() {
    final var expectedRestaurantPage = new PageImpl<Restaurant>(
            List.of(Restaurant.builder().id(1).name("First").build()));
    final var expectedDTO = new RestaurantDto(1, "First", null, null, 0,
            null, 0, null, 0,
            null, null, null);

    given(restaurantRepository.findAll(any(PageRequest.class))).willReturn(expectedRestaurantPage);
    given(restaurantMapper.toDto(any(Restaurant.class))).willReturn(expectedDTO);

    final var actualResult = restaurantService.findAll(0, 2);
    assertThat(actualResult).isNotNull();
    assertThat(actualResult).extracting(RestaurantDto::id).contains(1L);
    assertThat(actualResult.getContent()).hasSize(1);
  }

  @Test
  @DisplayName("Test findById() method will return correct restaurant")
  void testFindByIdSucceeds() {
    final var expectedRestaurant = Restaurant.builder().id(1).name("First").build();
    final var expectedDTO = new RestaurantDto(1, "First", null, null, 0,
            null, 0, null, 0,
            null, null, null);

    given(restaurantRepository.findById(anyLong())).willReturn(Optional.of(expectedRestaurant));
    given(restaurantMapper.toDto(any(Restaurant.class))).willReturn(expectedDTO);

    final var actualResult = restaurantService.findById(1L);

    assertThat(actualResult).extracting(RestaurantDto::id).isEqualTo(1L);
  }

  @Test
  @DisplayName("Test save() method will persist and return correct object containing userId")
  void testSaveSucceeds() {
    final var mockRestaurant = mock(Restaurant.class);
    final var expectedRestaurant = Restaurant.builder().id(1).ownerId("3423434").build();
    final var expectedDTO = new RestaurantDto(1, null, null, null, 0,
            null, 0, null, 0,
            null, null, null);

    given(userService.getCurrentUser()).willReturn(new User("3423434", "testUser"));
    given(restaurantMapper.from(any(RestaurantDto.class))).willReturn(mockRestaurant);
    given(mockRestaurant.withOwnerId(anyString())).willReturn(expectedRestaurant);
    given(restaurantMapper.toDto(any(Restaurant.class))).willReturn(expectedDTO);
    given(restaurantRepository.save(any(Restaurant.class))).willReturn(expectedRestaurant);

    restaurantService.save(expectedDTO);
    verify(restaurantMapper, times(1)).toDto(restaurantArgumentCaptor.capture());

    final var restaurantAfterSave = restaurantArgumentCaptor.getValue();
    assertThat(restaurantAfterSave).extracting(Restaurant::getOwnerId).isEqualTo("3423434");
  }

  @Test
  @DisplayName("Test update() method will update the restaurant entity and return the DTO")
  void testUpdateSucceeds() {
    final var expectedRestaurant = Restaurant.builder().id(1).ownerId("000").build();
    final var expectedDTO = new RestaurantDto(1, null, null, null, 0,
            null, 0, null, 0,
            null, null, null);

    given(restaurantRepository.findById(any())).willReturn(Optional.of(expectedRestaurant));
    given(userService.getCurrentUser()).willReturn(new User("000", "test"));
    given(restaurantMapper.from(any(RestaurantDto.class))).willReturn(expectedRestaurant);
    given(restaurantRepository.save(any(Restaurant.class))).willReturn(expectedRestaurant);
    given(restaurantMapper.toDto(any(Restaurant.class))).willReturn(expectedDTO);

    final var actualResult = restaurantService.update(1L, expectedDTO);

    assertThat(actualResult).isNotNull();
    assertThat(actualResult).extracting(RestaurantDto::id).isEqualTo(1L);
  }

  @Test
  @DisplayName("Test update() method will fail and  throw NotFoundException, when restaurant was not found")
  void testUpdateFailsRestaurantNotFound() {
    final var expectedDTO = new RestaurantDto(1, null, null, null, 0,
            null, 0, null, 0,
            null, null, null);

    given(restaurantRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      restaurantService.update(1L, expectedDTO);
    }).isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("Test update() method will fail and  throw InsufficientRightsException, when owner id and current user id do not match")
  void testUpdateFailsOwnerNotMatch() {
    final var expectedRestaurant = Restaurant.builder().id(1).ownerId("000").build();
    final var expectedDTO = new RestaurantDto(1, null, null, null, 0,
            null, 0, null, 0,
            null, null, null);

    given(restaurantRepository.findById(anyLong())).willReturn(Optional.of(expectedRestaurant));
    given(userService.getCurrentUser()).willReturn(new User("324", "test"));

    assertThatThrownBy(() -> {
      restaurantService.update(1L, expectedDTO);
    }).isInstanceOf(InsufficientRightsException.class);
  }

  @Test
  @DisplayName("Test deleteById() will succeed, the ID passed to delete function will match")
  void testDeleteByIdSucceeds() {
    final var expectedRestaurant = Restaurant.builder().id(1).ownerId("000").build();

    given(restaurantRepository.findById(anyLong())).willReturn(Optional.of(expectedRestaurant));
    given(userService.getCurrentUser()).willReturn(new User("000", "test"));

    restaurantService.deleteById(2L);

    verify(restaurantRepository, times(1)).deleteById(idArgumentCaptor.capture());
    assertThat(idArgumentCaptor.getValue()).isEqualTo(2L);
  }

  @Test
  @DisplayName("Test deleteById() will fail, when the restaurant not found")
  void testDeleteByIdFailsNotFound() {
    given(restaurantRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      restaurantService.deleteById(1L);
    }).isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("Test deleteById() will fail, when the owner id does not match to the current user's one")
  void testDeleteByIdFailsInsufficientRights() {
    final var expectedRestaurant = Restaurant.builder().id(1).ownerId("000").build();

    given(restaurantRepository.findById(anyLong())).willReturn(Optional.of(expectedRestaurant));
    given(userService.getCurrentUser()).willReturn(new User("4324", null));

    assertThatThrownBy(() -> {
      restaurantService.deleteById(1L);
    }).isInstanceOf(InsufficientRightsException.class);
  }

  @Test
  @DisplayName("Test deleteAll() will succeed, repository.deleteAll() will be called once")
  void testDeleteAllSucceeds() {
    restaurantService.deleteAll();

    verify(restaurantRepository, times(1)).deleteAll();
  }
}
