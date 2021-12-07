package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.MenuItemDto;
import com.akmal.springfoodieappbackend.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.akmal.springfoodieappbackend.shared.http.ResponseEntityConverter.ok;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 05/12/2021 - 8:05 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(MenuItemController.BASE_URL)
@RequiredArgsConstructor
public class MenuItemController {
  public static final String BASE_URL = "/api/menus/{menuId}/menu-items";
  private final MenuItemService menuItemService;

  @GetMapping("/{id}")
  public ResponseEntity<MenuItemDto> findById(@PathVariable Long id) {
    return ok(this.menuItemService.findById(id));
  }

  @GetMapping
  public List<MenuItemDto> findByMenuId(@PathVariable Long menuId) {
    return this.menuItemService.findByMenuId(menuId);
  }

  @PostMapping
  public MenuItemDto save(@RequestBody MenuItemDto menuItemDto) {
    return this.menuItemService.save(menuItemDto);
  }

  @PutMapping("/{id}")
  public MenuItemDto update(@RequestBody MenuItemDto menuItemDto, @PathVariable Long id) {
    return this.menuItemService.update(menuItemDto, id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    this.menuItemService.deleteById(id);
  }
}
