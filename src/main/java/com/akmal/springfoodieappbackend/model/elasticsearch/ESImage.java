package com.akmal.springfoodieappbackend.model.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 24/01/2022 - 20:35
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class ESImage {
  @Field(type = FieldType.Keyword)
  private final String url;

  @Field(type = FieldType.Text)
  private final String title;

  @Field(type = FieldType.Keyword)
  private final String id;
}
