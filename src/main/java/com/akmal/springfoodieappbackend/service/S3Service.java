package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.exception.FileException;
import com.akmal.springfoodieappbackend.model.FileMetaData;
import com.akmal.springfoodieappbackend.shared.validation.file.FileValidatorFactory;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * An implementation of the {@link FileService} that works with AWS S3
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:40 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
class S3Service implements FileService {

  private final AmazonS3 s3Client;

  @Value("${cloud.aws.bucket.name}")
  private String bucketName;

  @Override
  public FileMetaData upload(MultipartFile file, FileType type) {
    FileValidatorFactory.getInstance(type).validate(file);

    final var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

    final var key = String.format("%s-%s.%s", UUID.randomUUID(), type, fileExtension);

    final var metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    metadata.setContentType(file.getContentType());

    try {
      this.s3Client.putObject(bucketName, key, file.getInputStream(), metadata);
    } catch (IOException e) {
      log.error(
          "Exception occurred uploading files to S3 bucket {}. Message: {}",
          this.bucketName,
          e.getMessage());
      throw new FileException("There was an error while uploading a file");
    }

    this.s3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

    final var url = this.s3Client.getUrl(bucketName, key).toString();
    return new FileMetaData(url, key);
  }

  @Override
  public void deleteById(String key) {
    try {
      this.s3Client.deleteObject(this.bucketName, key);
    } catch (SdkClientException e) {
      log.error(
          "Exception occurred deleting file with key {} in S3 bucket {}. Message: {}",
          key,
          this.bucketName,
          e.getMessage());
      throw new FileException("There was an error while deleting your file");
    }
  }
}
