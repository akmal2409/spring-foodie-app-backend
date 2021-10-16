package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents restaurant's category.
 * A restaurant may relate to several categories, whereas
 * one category may relate to multiple restaurants, hence,
 * {@code @ManyToMany} was used.
 * @author Akmal ALikhujaev
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
  @ManyToMany(fetch = FetchType.LAZY)
  private final Set<Restaurant> restaurants = new HashSet<>();
}
