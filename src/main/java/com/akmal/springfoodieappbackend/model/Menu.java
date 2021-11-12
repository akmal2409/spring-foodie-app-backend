package com.akmal.springfoodieappbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
@With
public class Menu {
  @NotBlank(message = "Name is required")
  private final String name;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private final Category category;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "menu")
  private List<MenuItem> menuItems = new ArrayList<>();
  @ManyToOne(fetch = FetchType.LAZY)
  private final Restaurant restaurant;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * <strong>addMenuItem(MenuItem menuItem)</strong> is a helper method that enables the client
   * to synchronize the both sides of the @OneToMany relationship.
   * The owning side of the relationship is the {@link MenuItem} class
   * and therefore, it should manage the persistence of a menu by itself.
   * @param menuItem - object representing a single menu item in the menu
   * @return immutable copy of the {@link MenuItem} instance with the menu reference.
   */
  public MenuItem addMenuItem(MenuItem menuItem) {
    final var menuItemWithMenu = menuItem.withMenu(this);
    this.menuItems.add(menuItemWithMenu);
    return menuItemWithMenu;
  }
}
