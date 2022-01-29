package com.akmal.springfoodieappbackend.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

/**
 * Class represents opening times of a facility. Day is an integer from 1 to 7 where 1 is Monday.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@With
public class OpeningTime {
  public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

  @Column(name = "opening_day")
  private final int day;

  private final LocalTime openFrom;
  private final LocalTime openTill;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
  @Setter
  private Restaurant restaurant;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
        this.openFrom.format(formatter).toString(), this.openTill.format(formatter).toString());
  }
}
