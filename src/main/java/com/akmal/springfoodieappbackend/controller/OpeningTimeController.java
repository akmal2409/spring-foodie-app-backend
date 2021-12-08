package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.OpeningTimeDto;
import com.akmal.springfoodieappbackend.service.OpeningTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The controller represents a set of endpoints exposed for manipulation of the {@link
 * com.akmal.springfoodieappbackend.model.OpeningTime} entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 08/12/2021 - 8:26 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RequestMapping(OpeningTimeController.BASE_URL)
@RequiredArgsConstructor
public class OpeningTimeController {
  public static final String BASE_URL = "/api/opening-times";
  private final OpeningTimeService openingTimeService;

  @PostMapping
  public OpeningTimeDto save(@RequestBody @Valid OpeningTimeDto timeDto) {
    return this.openingTimeService.save(timeDto);
  }

  @PutMapping("/{id}")
  public OpeningTimeDto update(@RequestBody @Valid OpeningTimeDto timeDto, @PathVariable Long id) {
    return this.openingTimeService.update(timeDto, id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    this.openingTimeService.deleteById(id);
  }
}
