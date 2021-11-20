package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The class represents the image class
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 6:35 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Image {
  private long id;
  private final String url;
  private final String title;
}
