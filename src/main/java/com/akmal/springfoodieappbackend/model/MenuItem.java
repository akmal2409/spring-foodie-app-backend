package com.akmal.springfoodieappbackend.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Class represents a single Menu Item which contains available for the selection option sets.
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
public class MenuItem {
  @NotBlank(message = "Name is required")
  private final String name;

  @DecimalMin(value = "0.0", message = "Base price must be greater than 0")
  private final BigDecimal basePrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id", referencedColumnName = "id")
  private final Menu menu;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private final Image thumbnailImage;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private final Image fullImage;

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "menuItem",
      orphanRemoval = true)
  @OrderBy("orderPosition asc")
  @Builder.Default
  private List<OptionSet> optionSets = new ArrayList<>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * <strong>addOptionSet(OptionSet optionSet)</strong> is a helper method that enables the client
   * to synchronize the both sides of the @OneToMany relationship. The owning side of the
   * relationship is the {@link OptionSet} class and therefore, it should manage the persistence of
   * a menu item by itself.
   *
   * @param optionSet - object representing a set of {@link Option}'s instances
   * @return immutable copy of the {@link OptionSet} instance with the menu item reference.
   */
  public OptionSet addOptionSet(OptionSet optionSet) {
    final var optionSetWithMenuItem = optionSet.withMenuItem(this);
    this.optionSets.add(optionSetWithMenuItem);
    return optionSetWithMenuItem;
  }
}
