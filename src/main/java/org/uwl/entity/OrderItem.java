package org.uwl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long quantity;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private Double total;

  @ManyToOne private Product product;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
