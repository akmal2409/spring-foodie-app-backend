package com.akmal.springfoodieappbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class represents restaurant's category. A restaurant may relate to several categories, whereas
 * one category may relate to multiple restaurants, hence, {@code @ManyToMany} was used.
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
@With
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

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "icon_id", referencedColumnName = "id", nullable = true)
  private Image icon;

  private int groupId;
}
