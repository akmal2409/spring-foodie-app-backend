package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.exception.FileException;
import com.akmal.springfoodieappbackend.exception.InvalidFileException;
import com.akmal.springfoodieappbackend.model.FileMetaData;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the AWS S3 persistence layer using localstack running in a docker
 * container. Localstack emulates aws API and in general the whole cloud environment.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 04/12/2021 - 6:04 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles(profiles = {"integration-test", "integration-test-h2"})
class S3ServiceIT {
  public static final String BUCKET_NAME = "test-bucket";
  @Container
  static LocalStackContainer localStack =
          new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
                  .withServices(LocalStackContainer.Service.S3);

  @Autowired
  S3Service s3Service;

  @Autowired
  AmazonS3 s3Client;

  @TestConfiguration
  static class AwsTestConfig {
    @Bean
    public AmazonS3 amazonS3() {
      return AmazonS3ClientBuilder.standard()
              .withCredentials(localStack.getDefaultCredentialsProvider())
              .withEndpointConfiguration(localStack.getEndpointConfiguration(LocalStackContainer.Service.S3))
              .build();
    }
  }

  @BeforeAll
  static void beforeAll() throws IOException, InterruptedException {
    localStack.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", BUCKET_NAME);
  }
  /**
   * The method is responsible for setting the configuration properties needed for AWS SDK
   *
   * @param registry - Registry containing all the properties
   */
  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("cloud.aws.bucket.name", () -> BUCKET_NAME);
    registry.add("cloud.aws.s3.endpoint", () -> localStack.getEndpointConfiguration(LocalStackContainer.Service.S3));
    registry.add("cloud.aws.s3.default-endpoint", () -> localStack.getEndpointConfiguration(LocalStackContainer.Service.S3));
    registry.add("cloud.aws.credentials.access-key", localStack::getAccessKey);
    registry.add("cloud.aws.credentials.secret-key", localStack::getSecretKey);
    registry.add("cloud.aws.region.static" , localStack::getRegion);
    registry.add("cloud.aws.region.auto" , () -> false);
    registry.add("cloud.aws.stack.auto" , () -> false);
  }

  @Test
  @DisplayName("Test upload() should succeed and upload file to S3 bucket")
  void shouldUpload() throws IOException {
    File file = ResourceUtils.getFile("classpath:test-files/test-image.png");
    MultipartFile expectedMultipartFile = new MockMultipartFile("file", "testfile.png",
            MediaType.IMAGE_PNG_VALUE,
            Files.readAllBytes(file.toPath()));

    final FileMetaData fileMetaData = s3Service.upload(expectedMultipartFile, FileService.FileType.IMAGE);
    String fileExtension = StringUtils.getFilenameExtension(fileMetaData.key());

    final S3Object actualFileObject = s3Client.getObject(BUCKET_NAME, fileMetaData.key());

    assertThat(fileExtension).isNotNull().isEqualTo("png");
    assertThat(fileMetaData.location()).isNotNull().hasSizeGreaterThan(5);
    assertThat(actualFileObject.getObjectMetadata().getContentLength()).isEqualTo(expectedMultipartFile.getSize());
    assertThat(actualFileObject.getObjectMetadata().getContentType()).isEqualTo(expectedMultipartFile.getContentType());
  }

  @Test
  @DisplayName("Test upload() should fail because file does not have extension")
  void shouldFailUploadValidation() throws IOException {
    File file = ResourceUtils.getFile("classpath:test-files/test-image");
    MultipartFile expectedMultipartFile = new MockMultipartFile("file", "testfile",
            MediaType.IMAGE_PNG_VALUE,
            Files.readAllBytes(file.toPath()));

    assertThatThrownBy(() -> {
      s3Service.upload(expectedMultipartFile, FileService.FileType.IMAGE);
    }).isInstanceOf(FileException.class);
  }

  @Test
  @DisplayName("Test upload() should fail because extension is not supported")
  void shouldFailUploadInvalidExtension() throws IOException {
    File file = ResourceUtils.getFile("classpath:test-files/test-image.png");
    MultipartFile expectedMultipartFile = new MockMultipartFile("file", "testfile.html",
            MediaType.IMAGE_PNG_VALUE,
            Files.readAllBytes(file.toPath()));

    assertThatThrownBy(() -> {
      s3Service.upload(expectedMultipartFile, FileService.FileType.IMAGE);
    }).isInstanceOf(InvalidFileException.class);
  }

  @Test
  @DisplayName("Test deleteById() should succeed")
  void deleteById() throws IOException {
    String key = uploadDummyFile();

    assertThat(s3Client.doesObjectExist(BUCKET_NAME, key)).isTrue();

    s3Service.deleteById(key);

    assertThat(s3Client.doesObjectExist(BUCKET_NAME, key)).isFalse();
  }

  private String uploadDummyFile() throws IOException {
    File file = ResourceUtils.getFile("classpath:test-files/test-image.png");
    MultipartFile expectedMultipartFile = new MockMultipartFile("file", "testfile.png",
            MediaType.IMAGE_PNG_VALUE,
            Files.readAllBytes(file.toPath()));

    final var fileExtension = StringUtils.getFilenameExtension(expectedMultipartFile.getOriginalFilename());

    final var key = String.format("%s-%s.%s", UUID.randomUUID().toString(), "IMAGE", fileExtension);

    final var metadata = new ObjectMetadata();
    metadata.setContentLength(expectedMultipartFile.getSize());
    metadata.setContentType(expectedMultipartFile.getContentType());

    this.s3Client.putObject(BUCKET_NAME, key, expectedMultipartFile.getInputStream(), metadata);
    return key;
  }
}
