package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.model.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

/**
 * The FileService interface represents a contract needed to upload the files
 * to the external service.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:33 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface FileService {


  enum FileType {
    IMAGE, PDF
  }

  /**
   * Abstract method that every class must implement in order
   * to let client to upload the files.
   * @param file {@link MultipartFile} object
   * @param type {@link FileType} object
   * @return {@link FileMetaData} object.
   */
  FileMetaData upload(MultipartFile file, FileType type);

  /**
   * Method is responsible for deleting the file by key.
   * @param key of the file
   */
  void deleteById(String key);
}
