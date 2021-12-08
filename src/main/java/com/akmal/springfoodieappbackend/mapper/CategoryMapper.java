package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.CategoryDto;
import com.akmal.springfoodieappbackend.dto.ImageDto;
import com.akmal.springfoodieappbackend.model.Category;
import com.akmal.springfoodieappbackend.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Interface represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts Category Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:48 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CategoryMapper {

  @Mapping(target = "icon", expression = "java(mapImageToDto(category.getIcon()))")
  public abstract CategoryDto toDto(Category category);

  @Mapping(target = "icon", expression = "java(mapToImage(categoryDto.icon()))")
  public abstract Category from(CategoryDto categoryDto);

  protected ImageDto mapImageToDto(Image image) {
    if (image == null) {
      return null;
    }
    return ImageDto.fromImage(image);
  }

  protected Image mapToImage(ImageDto imageDto) {
    if (imageDto == null) {
      return null;
    }
    return imageDto.toImage();
  }
}
