package com.akmal.springfoodieappbackend.dto;

import com.akmal.springfoodieappbackend.model.Image;

/**
 * The record class that represents the {@link com.akmal.springfoodieappbackend.model.Image}
 * entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 6:40 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record ImageDto(String id,
                       String url,
                       String title) {

  /**
   * Static factory method is responsible for converting the {@link Image}
   * to {@link ImageDto} class.
   *
   * @param image {@link Image} object
   * @return {@link ImageDto} object.
   */
  public static ImageDto fromImage(Image image) {
    return new ImageDto(image.getId(), image.getUrl(), image.getTitle());
  }

  /**
   * The method is responsible for converting the DTO
   * object to {@link Image} class object.
   *
   * @return {@link Image} object
   */
  public Image toImage() {
    return new Image(this.id, this.url, this.title);
  }
}
