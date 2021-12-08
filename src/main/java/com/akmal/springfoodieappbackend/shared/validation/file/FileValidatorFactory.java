package com.akmal.springfoodieappbackend.shared.validation.file;

import com.akmal.springfoodieappbackend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Factory class that is used to get instances of classes that implement {@link FileValidator}
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:40 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
public class FileValidatorFactory {
  private static final Map<FileService.FileType, FileValidator> COMPONENT_CACHE = new HashMap<>();

  @Autowired
  private FileValidatorFactory(List<FileValidator> beans) {
    for (FileValidator validator : beans) {
      COMPONENT_CACHE.put(validator.getType(), validator);
    }
  }

  public static FileValidator getInstance(FileService.FileType type) {
    return Optional.ofNullable(COMPONENT_CACHE.get(type))
        .orElseThrow(() -> new IllegalArgumentException("The type of validator does not exist"));
  }
}
