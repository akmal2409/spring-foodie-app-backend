package com.akmal.springfoodieappbackend.shared.validation.file;

import com.akmal.springfoodieappbackend.exception.FileException;
import com.akmal.springfoodieappbackend.exception.InvalidFileException;
import com.akmal.springfoodieappbackend.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * The class implements {@link FileValidator} and is used to validate the contents of the image file
 * sent by the client.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:52 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Component
class ImageFileValidator implements FileValidator {
  private static final Pattern EXTENSION_PATTERN =
      Pattern.compile("(gif|jpe?g|tiff?|png|webp|bmp)$");
  /**
   * {@inheritDoc}
   *
   * @param file {@link MultipartFile} object
   * @throws InvalidFileException
   * @throws FileException
   */
  @Override
  public void validate(MultipartFile file) throws InvalidFileException, FileException {
    Objects.requireNonNull(file, "File cannot be null");
    final var fileExtension =
        Optional.ofNullable(StringUtils.getFilenameExtension(file.getOriginalFilename()))
            .orElseThrow(() -> new FileException("The file extension could not be identified"));

    if (!EXTENSION_PATTERN.matcher(fileExtension).matches()) {
      throw new InvalidFileException(
          String.format("Invalid file extension. Provided %s", fileExtension));
    }
  }

  @Override
  public FileService.FileType getType() {
    return FileService.FileType.IMAGE;
  }
}
