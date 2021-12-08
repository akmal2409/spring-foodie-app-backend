package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.MenuDto;
import com.akmal.springfoodieappbackend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.akmal.springfoodieappbackend.shared.http.ResponseEntityConverter.ok;

/**
 * The {@link org.springframework.web.bind.annotation.RestController} class that manages client
 * interaction with the {@link com.akmal.springfoodieappbackend.model.Menu} entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 27/11/2021 - 2:14 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(MenuController.BASE_URL)
@RequiredArgsConstructor
public class MenuController {
  public static final String BASE_URL = "/api/restaurants/{restaurantId}/menus";
  private final MenuService menuService;

  @GetMapping
  public Page<MenuDto> findAllByRestaurant(
      @PathVariable long restaurantId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return this.menuService.findAllByRestaurantId(restaurantId, page, size);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuDto> findById(@PathVariable long id) {
    return ok(this.menuService.findById(id));
  }

  @PostMapping
  public MenuDto save(@RequestBody @Valid MenuDto menuDto) {
    return this.menuService.save(menuDto);
  }

  @PutMapping("/{id}")
  public MenuDto update(@RequestBody @Valid MenuDto menuDto, @PathVariable long id) {
    return this.menuService.update(menuDto, id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable long id) {
    this.menuService.deleteById(id);
  }
}
