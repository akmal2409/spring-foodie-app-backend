package com.akmal.springfoodieappbackend.shared.validation.file;

import com.akmal.springfoodieappbackend.exception.FileException;
import com.akmal.springfoodieappbackend.exception.InvalidFileException;
import com.akmal.springfoodieappbackend.service.FileService;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface serves as contract for file validators.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:43 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface FileValidator {

  /**
   * Method is responsible for validating the fields and contents of the {@link MultipartFile}
   * uploaded by the client.
   *
   * @param file {@link MultipartFile} object
   * @throws InvalidFileException if file does not meet requirements.
   * @throws FileException in case file processing fails.
   */
  void validate(MultipartFile file) throws InvalidFileException, FileException;

  FileService.FileType getType();
}
