package org.uwl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String addressLine1;

  @Column(nullable = false)
  private String addressLine2;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String postcode;

  @Column(nullable = false)
  private String country;

  @ManyToOne private User user;
}
