package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
