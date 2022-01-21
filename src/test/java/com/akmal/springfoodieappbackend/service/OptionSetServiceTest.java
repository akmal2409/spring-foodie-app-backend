package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.MenuItemConfigurationDto;
import com.akmal.springfoodieappbackend.exception.InvalidConfigurationException;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.OptionSetMapper;
import com.akmal.springfoodieappbackend.model.Option;
import com.akmal.springfoodieappbackend.model.OptionSet;
import com.akmal.springfoodieappbackend.repository.OptionRepository;
import com.akmal.springfoodieappbackend.repository.OptionSetRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/01/2022 - 19:50
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class OptionSetServiceTest {
  @Mock MenuItemService MenuItemService;
  @Mock RestaurantService restaurantService;
  @Mock OptionSetMapper optionSetMapper;
  @Mock OptionSetRepository optionSetRepository;
  @Mock OptionRepository optionRepository;
  @Spy TransactionRunner transactionRunner = new TransactionRunner();
  @InjectMocks OptionSetService optionSetService;

  @Test
  @DisplayName("validateSelectedOptions() Succeeds when provided valid data")
  void validateSelectedOptionsSucceeds() {
    Option option = Option.builder().id(1l).build();
    OptionSet optionSet =
        OptionSet.builder()
            .id(1l)
            .exclusive(false)
            .maximumOptionsSelected(-1)
            .options(List.of(option))
            .build();

    List<MenuItemConfigurationDto.SelectedOption> selectedOptions = new LinkedList<>();
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(1l, 1l));

    when(optionRepository.findById(anyLong())).thenReturn(Optional.of(option));
    when(optionSetRepository.findById(anyLong())).thenReturn(Optional.of(optionSet));

    this.optionSetService.validateSelectedOptions(selectedOptions);
  }

  @Test
  @DisplayName("validateSelectedOptions() Fails when option does not belong to the set")
  void validateSelectedOptionsFailOptionNotInSet() {
    Option option = Option.builder().id(1l).build();
    OptionSet optionSet =
        OptionSet.builder().id(1l).exclusive(false).maximumOptionsSelected(-1).build();

    List<MenuItemConfigurationDto.SelectedOption> selectedOptions = new LinkedList<>();
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(1l, 1l));

    when(optionRepository.findById(anyLong())).thenReturn(Optional.of(option));
    when(optionSetRepository.findById(anyLong())).thenReturn(Optional.of(optionSet));

    assertThatThrownBy(
            () -> {
              this.optionSetService.validateSelectedOptions(selectedOptions);
            })
        .isInstanceOf(InvalidConfigurationException.class);
  }

  @Test
  @DisplayName("validateSelectedOptions() Fails when option is null")
  void validateSelectedOptionsFailOptionNull() {
    List<MenuItemConfigurationDto.SelectedOption> selectedOptions = new LinkedList<>();
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(1l, 1l));

    when(optionRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> {
              this.optionSetService.validateSelectedOptions(selectedOptions);
            })
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("validateSelectedOptions() Fails when option set is null")
  void validateSelectedOptionsFailSetNull() {
    Option option = Option.builder().id(1l).build();
    List<MenuItemConfigurationDto.SelectedOption> selectedOptions = new LinkedList<>();
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(1l, 1l));

    when(optionRepository.findById(anyLong())).thenReturn(Optional.of(option));
    when(optionSetRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> {
              this.optionSetService.validateSelectedOptions(selectedOptions);
            })
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("validateSelectedOptions() Fails when selected options are null")
  void validateSelectedOptionsFailSelectedOptionsIsNull() {
    assertThatThrownBy(
            () -> {
              this.optionSetService.validateSelectedOptions(null);
            })
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName(
      "validateSelectedOptions() Fails when the set is exclusive and two options are associated"
          + " with the set")
  void validateSelectedOptionsFailExclusivenessViolated() {
    Option option = Option.builder().id(1l).build();
    Option option2 = Option.builder().id(2l).build();
    OptionSet optionSet =
        OptionSet.builder()
            .id(1l)
            .exclusive(true)
            .maximumOptionsSelected(-1)
            .options(List.of(option, option2))
            .build();

    List<MenuItemConfigurationDto.SelectedOption> selectedOptions = new LinkedList<>();
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(1l, 1l));
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(2l, 1l));

    when(optionRepository.findById(1l)).thenReturn(Optional.of(option));
    when(optionRepository.findById(2l)).thenReturn(Optional.of(option2));
    when(optionSetRepository.findById(anyLong())).thenReturn(Optional.of(optionSet));

    assertThatThrownBy(
            () -> {
              this.optionSetService.validateSelectedOptions(selectedOptions);
            })
        .isInstanceOf(InvalidConfigurationException.class);
  }

  @Test
  @DisplayName("validateSelectedOptions() fails when maximum number of options is exceeded")
  void validateSelectedOptionsFailMaxNumberExceeded() {
    Option option = Option.builder().id(1l).build();
    Option option2 = Option.builder().id(2l).build();
    OptionSet optionSet =
        OptionSet.builder()
            .id(1l)
            .exclusive(false)
            .maximumOptionsSelected(1)
            .options(List.of(option, option2))
            .build();

    List<MenuItemConfigurationDto.SelectedOption> selectedOptions = new LinkedList<>();
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(1l, 1l));
    selectedOptions.add(new MenuItemConfigurationDto.SelectedOption(2l, 1l));

    when(optionRepository.findById(1l)).thenReturn(Optional.of(option));
    when(optionRepository.findById(2l)).thenReturn(Optional.of(option2));
    when(optionSetRepository.findById(anyLong())).thenReturn(Optional.of(optionSet));

    assertThatThrownBy(
            () -> {
              this.optionSetService.validateSelectedOptions(selectedOptions);
            })
        .isInstanceOf(InvalidConfigurationException.class);
  }
}
