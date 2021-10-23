package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Class represents the restaurant's menu with a collection of menu items.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 17/10/2021 - 9:13 AM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Menu {
  @NotBlank(message = "Name is required")
  private final String name;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private final Category category;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "menu")
  private final List<MenuItem> menuItems;
  @ManyToOne(fetch = FetchType.LAZY)
  private final Restaurant restaurant;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
}
