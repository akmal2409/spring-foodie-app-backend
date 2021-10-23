package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class represents restaurant's category.
 * A restaurant may relate to several categories, whereas
 * one category may relate to multiple restaurants, hence,
 * {@code @ManyToMany} was used.
 *
 * @author Akmal ALikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @NotBlank(message = "Name is required")
  @Size(min = 3)
  private String name;
  @NotBlank(message = "Description is required")
  @Size(min = 5)
  private String description;
}
