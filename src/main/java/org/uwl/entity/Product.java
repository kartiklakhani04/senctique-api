package org.uwl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

  public enum Category {
    Men,
    Women,
    Unisex,
    Luxury
  }

  public enum Gender {
    Men,
    Women,
    Unisex
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String brand;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false, length = 500)
  private String imageUrl;

  @Column(nullable = false)
  private String topNotes;

  @Column(nullable = false)
  private String middleNotes;

  @Column(nullable = false)
  private String baseNotes;

  @Column(nullable = false)
  private String concentration;

  @Column(nullable = false)
  private String longevity;

  @Column(nullable = false)
  private String size;

  @Column(nullable = false)
  private Double rating;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String sillage;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private String fragranceType;

  @Column(name = "usage_info", nullable = false)
  private String usage;

  @Column(nullable = false)
  private String packaging;

  @Column(nullable = false)
  private String ingredients;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;

  @Column(nullable = false)
  private Long stock;
}
