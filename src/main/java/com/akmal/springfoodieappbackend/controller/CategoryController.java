package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.CategoryDto;
import com.akmal.springfoodieappbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.akmal.springfoodieappbackend.shared.http.ResponseEntityConverter.ok;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 5:23 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(CategoryController.BASE_URL)
@RequiredArgsConstructor
public class CategoryController {
  public static final String BASE_URL = "/api/categories";
  private final CategoryService categoryService;


  @GetMapping
  public List<CategoryDto> findAll() {
    return this.categoryService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> findById(@PathVariable long id) {
    return ok(this.categoryService.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDto save(@RequestBody CategoryDto categoryDto) {
    return this.categoryService.save(categoryDto);
  }

  @PutMapping("/{id}")
  public CategoryDto update(@PathVariable long id,
                            @RequestBody CategoryDto categoryDto) {
    return this.categoryService.update(categoryDto, id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable long id) {
    this.categoryService.deleteById(id);
  }

}
