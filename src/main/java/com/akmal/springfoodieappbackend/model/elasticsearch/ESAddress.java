package com.akmal.springfoodieappbackend.model.elasticsearch;

import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.geo.Point;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 24/01/2022 - 20:22
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@AllArgsConstructor
public class ESAddress {
  @Field(type = FieldType.Keyword)
  private final String country;

  @Field(type = FieldType.Keyword)
  private final String city;

  @Field(type = FieldType.Keyword)
  private final String postCode;

  @Field(type = FieldType.Text)
  private final String street;

  @Field(type = FieldType.Text)
  private final String addition;

  @Field(type = FieldType.Keyword)
  private final String apartmentNumber;

  @GeoPointField private final Point location;
}
