package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.ImageDto;
import com.akmal.springfoodieappbackend.model.Image;
import com.akmal.springfoodieappbackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * The class is {@link RestController} that enables CRUD operations for
 * {@link com.akmal.springfoodieappbackend.model.Image} entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 6:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(ImageController.BASE_URL)
@RequiredArgsConstructor
public class ImageController {
  public static final String BASE_URL = "/api/images";
  private final ImageService imageService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ImageDto upload(@RequestParam("file") MultipartFile image, @RequestParam("title") String title) {
    return this.imageService.uploadAndSave(image, title);
  }

  @DeleteMapping("/{key}")
  public void delete(@PathVariable String key) {
    this.imageService.deleteFileByKey(key);
  }

  @GetMapping
  public Iterable<Image> findAll() {
    return this.imageService.findAll();
  }
}
