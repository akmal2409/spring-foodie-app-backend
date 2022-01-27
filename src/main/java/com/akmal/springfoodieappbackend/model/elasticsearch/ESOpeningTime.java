package com.akmal.springfoodieappbackend.model.elasticsearch;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 24/01/2022 - 20:30
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@AllArgsConstructor
public class ESOpeningTime {
  @Field(type = FieldType.Integer)
  private final int day;

  @Field(type = FieldType.Date, format = DateFormat.hour_minute_second_fraction)
  private final LocalTime openFrom;

  @Field(type = FieldType.Keyword, format = DateFormat.hour_minute_second_fraction)
  private final LocalTime openTill;

  @Field(type = FieldType.Long)
  private long id;
}
