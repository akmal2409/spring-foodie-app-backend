package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.CategoryDto;
import com.akmal.springfoodieappbackend.dto.ImageDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.CategoryMapper;
import com.akmal.springfoodieappbackend.model.Category;
import com.akmal.springfoodieappbackend.model.Image;
import com.akmal.springfoodieappbackend.repository.CategoryRepository;
import com.akmal.springfoodieappbackend.repository.ImageRepository;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The integration test class that tests the {@link CategoryService} class using {@code
 * Testcontainers} framework that spins up the database (MySQL) in a docker container. Extends
 * {@link com.akmal.springfoodieappbackend.service.BaseContainerMysqlTest} that sets up docker
 * container.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 02/12/2021 - 6:46 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Transactional
@ActiveProfiles(profiles = {"integration-test", "aws-disabled", "integration-test-mysql"})
class CategoryServiceIT extends BaseContainerMysqlTest {
  @Autowired CategoryService categoryService;
  @Autowired CategoryRepository categoryRepository;
  @Autowired ImageRepository imageRepository;
  @Autowired CategoryMapper categoryMapper;

  @AfterEach
  public void setup() {
    imageRepository.deleteAll();
    categoryRepository.deleteAll();

    assertThat(categoryRepository.findAll()).isEmpty();
  }

  @Test
  @DisplayName("Test findAll() should succeed and return 1 element")
  void shouldFindAll() {
    final var savedImage = createAndSaveImage();
    final var expectedCategory =
        Category.builder()
            .groupId(1)
            .description("Test desc")
            .name("test name")
            .icon(savedImage)
            .build();

    categoryRepository.save(expectedCategory);

    List<CategoryDto> categories = categoryService.findAll();

    assertThat(categories)
        .hasSize(1)
        .element(0)
        .usingRecursiveComparison()
        .isEqualTo(expectedCategory);
  }

  @Test
  @DisplayName("Test findById() should pass and return correct dto object")
  void shouldFindById() {
    final var savedImage = createAndSaveImage();
    final var expectedCategory =
        Category.builder()
            .groupId(1)
            .description("Test desc")
            .name("test name")
            .icon(savedImage)
            .build();

    long id = categoryRepository.save(expectedCategory).getId();

    CategoryDto actualCategoryDto = categoryService.findById(id);

    assertThat(actualCategoryDto)
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(expectedCategory);
  }

  @Test
  @DisplayName("Test save() should succeed and generate id for an entity")
  void shouldSave() {
    final var expectedCategory =
        new CategoryDto(
            null,
            "Test name",
            "tes fdsgfsdg fgfdst",
            0,
            new ImageDto("543454", "example.com", "title"));

    CategoryDto actualCategory = categoryService.save(expectedCategory);

    assertThat(actualCategory)
        .isNotNull()
        .extracting(CategoryDto::id)
        .is(new Condition<Long>(id -> id > 0, "ID is greater than 0"));
    assertThat(actualCategory)
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(expectedCategory);
  }

  @Test
  @DisplayName("Test update() should update the existing entity.")
  void shouldUpdate() {
    final var existingCategory =
        Category.builder()
            .id(3L)
            .groupId(1)
            .description("Test descgdfsg")
            .name("test name")
            .icon(createAndSaveImage())
            .build();
    final var savedCategory = categoryRepository.save(existingCategory);
    final var expectedCategory = categoryMapper.toDto(savedCategory.withName("Updated"));

    final var actualCategory = categoryService.update(expectedCategory, savedCategory.getId());

    assertThat(actualCategory)
        .isNotNull()
        .usingRecursiveComparison()
        .ignoringActualNullFields()
        .isEqualTo(expectedCategory);
  }

  @Test
  @DisplayName("Test update() should fail because entity does not exist")
  void shouldUpdateFailNoEntity() {
    final var expectedCategory =
        categoryMapper.toDto(
            Category.builder()
                .id(3L)
                .groupId(1)
                .description("Test desc")
                .name("test name")
                .icon(createAndSaveImage())
                .build());

    assertThatThrownBy(
            () -> {
              categoryService.update(expectedCategory, 3L);
            })
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("Test deleteById() should succeed and delete the entity")
  void shouldDeleteById() {
    final var existingCategory =
        Category.builder().groupId(1).description("Test desc").name("test name").icon(null).build();
    final var savedCategory = categoryRepository.save(existingCategory);

    categoryService.deleteById(savedCategory.getId());

    final var actualCategoryOptional = categoryRepository.findById(3L);

    assertThat(actualCategoryOptional).isEmpty();
  }

  @Test
  @DisplayName("Test removeImageReferences should succeed and remove all icons from categories")
  void shouldRemoveImageReferences() {
    final var savedImage = createAndSaveImage();
    final var categoryWithImage =
        Category.builder()
            .name("Test names")
            .description("Test description")
            .icon(savedImage)
            .build();
    final var savedCategory = categoryRepository.save(categoryWithImage);

    categoryService.removeImageReferences(savedImage.getId());

    final var actualCategory = categoryRepository.findById(savedCategory.getId());

    assertThat(actualCategory).isPresent().get().extracting(Category::getIcon).isNull();
  }

  Image createAndSaveImage() {
    final var savedImage = new Image("fdsafds", "fdsafds.com", "title");
    return imageRepository.save(savedImage);
  }
}
