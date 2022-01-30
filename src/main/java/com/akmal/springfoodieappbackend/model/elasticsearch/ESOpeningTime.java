package com.akmal.springfoodieappbackend.model.elasticsearch;

import com.akmal.springfoodieappbackend.model.OpeningTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@Getter
public class ESOpeningTime {
  @Field(type = FieldType.Integer)
  private final int day;

  @Field(type = FieldType.Date, format = DateFormat.hour_minute)
  @JsonFormat(pattern = "HH:mm", shape = Shape.STRING)
  private final LocalTime openFrom;

  @Field(type = FieldType.Date, format = DateFormat.hour_minute)
  @JsonFormat(pattern = "HH:mm", shape = Shape.STRING)
  private final LocalTime openTill;

  @Field(type = FieldType.Long)
  private long id;

  /**
   * The method returns the <em>openFrom</em> and <em>openTill</em> separated by a hyphen. E.g.
   * <strong>11:20 - 22:30</strong>
   *
   * @return
   */
  public String toRangeString() {

    return String.format(
        "%s - %s",
        this.openFrom.format(OpeningTime.formatter).toString(),
        this.openTill.format(OpeningTime.formatter).toString());
  }
}
