package org.uwl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
    name = "favorites",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "productId"})})
@Data
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;
  private Long productId;
}
