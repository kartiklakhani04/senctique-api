package org.uwl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uwl.bean.ShoppingBag;
import org.uwl.entity.Order;
import org.uwl.entity.OrderItem;
import org.uwl.entity.Product;
import org.uwl.service.ProductService;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired private ShoppingBag shoppingBag;
  @Autowired private ProductService productService;

  @GetMapping("/get")
  public ResponseEntity<Order> get() {
    return ResponseEntity.ok(shoppingBag.getOrder());
  }

  @PostMapping("/{productId}")
  public ResponseEntity<String> add(@PathVariable Long productId) {
    Product product = productService.findById(productId);
    if (product.getStock() >= 1) {
      OrderItem newItem = new OrderItem();
      newItem.setPrice(product.getPrice());
      newItem.setQuantity(1L);
      newItem.setTotal(product.getPrice());
      newItem.setProduct(product);
      shoppingBag.getOrder().getOrderItems().add(newItem);
      updateOrderTotal();
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(404).body("Product out of stock");
    }
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> remove(@PathVariable Long productId) {
    shoppingBag
        .getOrder()
        .getOrderItems()
        .removeIf(item -> item.getProduct().getId().equals(productId));
    updateOrderTotal();
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/quantity/{productId}")
  public ResponseEntity<Void> updateQuantity(
      @PathVariable Long productId, @RequestParam Long quantity) {
    OrderItem orderItem =
        shoppingBag.getOrder().getOrderItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);
    if (orderItem != null) {
      orderItem.setQuantity(quantity);
      orderItem.setTotal(orderItem.getPrice() * quantity);
    } else {
      return ResponseEntity.notFound().build();
    }

    updateOrderTotal();
    return ResponseEntity.noContent().build();
  }

  private void updateOrderTotal() {
    double total =
        shoppingBag.getOrder().getOrderItems().stream().mapToDouble(OrderItem::getTotal).sum();
    shoppingBag.getOrder().setTotal(total);
  }
}
