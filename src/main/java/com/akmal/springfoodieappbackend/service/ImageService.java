package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.ImageDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.model.FileMetaData;
import com.akmal.springfoodieappbackend.model.Image;
import com.akmal.springfoodieappbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
  private final RestaurantService restaurantService;
  private final FileService fileService;
  private final TransactionRunner transactionRunner;
  private final CategoryService categoryService;

  /**
   * Method is responsible for uploading new images with a given title
   * by means of the underlying file service provider.
   * Thereafter, it prepares the {@link Image} entity object that contains
   * fields like url, key as well as title.
   * Url and key are gotten from the {@link FileMetaData} returned by the
   * {@link FileService#upload(MultipartFile, FileService.FileType)}.
   *
   * @param image {@link MultipartFile} file object
   * @param title of the file
   * @return image entity in the database as DTO object {@link ImageDto}
   */
  @Transactional
  public ImageDto uploadAndSave(MultipartFile image, String title) {
    final FileMetaData imageMetaData = this.fileService.upload(image, FileService.FileType.IMAGE);

    final var imageEntity = new Image(imageMetaData.key(), imageMetaData.location(), title);

    return ImageDto.fromImage(this.imageRepository.save(imageEntity));
  }

  /**
   * Method is responsible for deleting the image by ID.
   *
   * @param key of the image
   * @throws NotFoundException if image is not found
   */
  @Transactional
  public void deleteImageByKey(String key) {
    final var image = this.imageRepository.findById(key)
            .orElseThrow(() -> new NotFoundException(String.format("Image with ID %s was not found", key)));

    this.transactionRunner.runInTransaction(() -> this.removeReferences(image.getId()));
    this.imageRepository.deleteById(image.getId());
    this.fileService.deleteById(key);
  }

  /**
   * The method is responsible for deleting all the references to the particular image
   * in the tables like Restaurant, Categories and MenuItems to avoid violating
   * FK constraints.
   *
   * @param imageId of the deleted image
   */
  private void removeReferences(String imageId) {
    this.restaurantService.removeImageReferences(imageId);
    this.categoryService.removeImageReferences(imageId);
  }

  public Iterable<Image> findAll() {
    return this.imageRepository.findAll();
  }
}
