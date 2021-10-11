package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class OpeningTimes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private final int day;
  private final LocalTime openFrom;
  private final LocalTime openTill;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(referencedColumnName = "id")
  private Restaurant restaurant;
}
