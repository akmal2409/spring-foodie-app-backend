package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;


/**
 * Class represents opening times of a facility.
 * Day is an integer from 1 to 7 where 1 is Sunday.
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
public class OpeningTime {
  @Column(name = "openingDay")
  private final int day;
  private final LocalTime openFrom;
  private final LocalTime openTill;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(referencedColumnName = "id")
  private final Restaurant restaurant;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
}
