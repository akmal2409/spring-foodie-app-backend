package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ImageService class is a {@link org.springframework.stereotype.Service} that abstracts
 * the business logic of managing {@link com.akmal.springfoodieappbackend.model.Image}
 * entities from the controller.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 6:52 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;
}
