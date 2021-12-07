package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.CategoryDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.CategoryMapper;
import com.akmal.springfoodieappbackend.model.Category;
import com.akmal.springfoodieappbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The service class for a {@link com.akmal.springfoodieappbackend.model.Category} entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 5:23 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryMapper categoryMapper;
  private final CategoryRepository categoryRepository;

  /**
   * The method is responsible for finding all {@link
   * com.akmal.springfoodieappbackend.model.Category} objects in the database and mapping them to
   * {@link CategoryDto} objects.
   *
   * @return {@link CategoryDto} objects.
   */
  @Transactional(readOnly = true)
  public List<CategoryDto> findAll() {
    return this.categoryRepository.findAll().stream().map(this.categoryMapper::toDto).toList();
  }

  /**
   * The method is responsible for finding the {@link
   * com.akmal.springfoodieappbackend.model.Category} by ID. Returns null if not found.
   *
   * @param id of a {@link com.akmal.springfoodieappbackend.model.Category} entity
   * @return {@link CategoryDto} objects.
   */
  @Transactional(readOnly = true)
  public CategoryDto findById(long id) {
    return this.categoryRepository.findById(id).map(this.categoryMapper::toDto).orElse(null);
  }

  /**
   * The method is responsible for saving the {@link CategoryDto} object as {@link
   * com.akmal.springfoodieappbackend.model.Category} entity in the database.
   *
   * @param categoryDto {@link CategoryDto} object
   * @return {@link CategoryDto} object
   */
  @Transactional
  public CategoryDto save(CategoryDto categoryDto) {
    final var savedCategory = this.categoryRepository.save(this.categoryMapper.from(categoryDto));
    return this.categoryMapper.toDto(savedCategory);
  }

  /**
   * The method is responsible for updating existing {@link
   * com.akmal.springfoodieappbackend.model.Category} entity.
   *
   * @param categoryDto {@link CategoryDto} object
   * @param id of a {@link com.akmal.springfoodieappbackend.model.Category} entity.
   * @return {@link CategoryDto} object
   * @throws NotFoundException if entity was not found.
   */
  @Transactional
  public CategoryDto update(CategoryDto categoryDto, long id) {
    final var existingCategory =
        this.categoryRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(String.format("Category with ID %d was not found", id)));
    final var mappedCategory =
        this.categoryMapper.from(categoryDto).withId(existingCategory.getId());

    final var savedCategory = this.categoryRepository.save(mappedCategory);

    return this.categoryMapper.toDto(savedCategory);
  }

  /**
   * The method is responsible for deleting the {@link
   * com.akmal.springfoodieappbackend.model.Category} entity by ID. Succeeds even if the object does
   * not exist.
   *
   * @param id of {@link com.akmal.springfoodieappbackend.model.Category} entity.
   */
  @Transactional
  public void deleteById(long id) {
    this.categoryRepository.deleteById(id);
  }

  @Transactional
  public void removeImageReferences(String imageId) {
    final Iterable<Category> categories = this.categoryRepository.findAllByIconId(imageId);

    for (Category category : categories) {
      final var updatedCategory = category.withIcon(null);
      this.categoryRepository.save(updatedCategory);
    }
  }
}
