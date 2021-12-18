package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.OptionSetDto;
import com.akmal.springfoodieappbackend.model.OptionSet;
import com.akmal.springfoodieappbackend.service.OptionSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The class represents the controller layer of a {@link OptionSet} entity that exposes only
 * <strong>POST</strong> and <strong>PUT</strong> endpoints because it is fetched solely together
 * with the {@link com.akmal.springfoodieappbackend.model.MenuItem} entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 8:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(OptionSetController.BASE_URL)
@RequiredArgsConstructor
public class OptionSetController {
  public static final String BASE_URL = "/api/options-sets";
  private final OptionSetService optionSetService;

  @PostMapping
  public OptionSetDto save(@RequestBody @Valid OptionSetDto setDto) {
    return this.optionSetService.save(setDto);
  }

  @PutMapping("/{id}")
  public OptionSetDto update(@RequestBody @Valid OptionSetDto setDto, @PathVariable Long id) {
    return this.optionSetService.update(setDto, id);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    this.optionSetService.deleteById(id);
  }
}
