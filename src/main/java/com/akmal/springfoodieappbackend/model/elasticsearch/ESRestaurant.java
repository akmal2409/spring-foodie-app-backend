package com.akmal.springfoodieappbackend.model.elasticsearch;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 24/01/2022 - 20:16
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Document(indexName = "restaurants", createIndex = false)
@AllArgsConstructor
@Getter
public class ESRestaurant {
  @Field(type = FieldType.Long)
  private final Long id;

  @MultiField(
      mainField = @Field(type = FieldType.Text),
      otherFields = {@InnerField(suffix = "keyword", type = FieldType.Keyword)})
  private final String name;

  @Field(type = FieldType.Keyword)
  private final String phone;

  @Field(type = FieldType.Object)
  private final ESAddress address;

  @Field(type = FieldType.Integer)
  private final int averageDeliveryTime;

  @Field(type = FieldType.Double)
  private final BigDecimal deliveryCost;

  @Field(type = FieldType.Float)
  private final double minimumOrderValue;

  @Field(type = FieldType.Float)
  private final double rating;

  @Field(type = FieldType.Nested)
  private final List<ESOpeningTime> openingTimes;

  @Field(type = FieldType.Keyword)
  private final List<String> categories;

  @Field(type = FieldType.Object, index = false)
  private final ESImage thumbnailImage;

  @Field(type = FieldType.Object, index = false)
  private final ESImage fullImage;
}

/**
 * Search idea, search in multiple indices with different calls, then sort the results by the
 * highest relevance score and place them in such order ot the completions, constructing the
 * suggestion response.
 */
