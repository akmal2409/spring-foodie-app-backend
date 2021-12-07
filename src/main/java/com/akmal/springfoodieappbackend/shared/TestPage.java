package com.akmal.springfoodieappbackend.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is used as an extension to deserialize the Page class. Used only for testing.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 15/11/2021 - 5:27 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public class TestPage<T> extends PageImpl<T> {
  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public TestPage(
      @JsonProperty("content") List<T> content,
      @JsonProperty("number") int number,
      @JsonProperty("size") int size,
      @JsonProperty("totalElements") Long totalElements,
      @JsonProperty("pageable") JsonNode pageable,
      @JsonProperty("last") boolean last,
      @JsonProperty("totalPages") int totalPages,
      @JsonProperty("sort") JsonNode sort,
      @JsonProperty("first") boolean first,
      @JsonProperty("numberOfElements") int numberOfElements,
      @JsonProperty("empty") boolean empty) {

    super(content, PageRequest.of(number, size), totalElements);
  }

  public TestPage(List<T> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public TestPage(List<T> content) {
    super(content);
  }

  public TestPage() {
    super(new ArrayList<>());
  }
}
