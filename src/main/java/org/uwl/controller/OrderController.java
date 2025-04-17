package org.uwl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uwl.bean.ShoppingBag;
import org.uwl.email.OrderEmail;
import org.uwl.entity.Order;
import org.uwl.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired private OrderService orderService;
  @Autowired private ShoppingBag shoppingBag;
  @Autowired private OrderEmail orderEmail;

  @GetMapping("/{userId}")
  public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
    List<Order> orders = orderService.getOrdersByUserId(userId);
    if (orders.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/status/{id}")
  public ResponseEntity<Order> status(@PathVariable Long id) {
    Order order = orderService.findById(id);
    return ResponseEntity.ok(order);
  }

  @PostMapping("/save")
  public ResponseEntity<Long> save() {
    Order order = shoppingBag.getOrder();
    Order savedOrder = orderService.save(order);
    shoppingBag.clean();
    orderEmail.sendOrderEmail(order);
    return ResponseEntity.ok(savedOrder.getId());
  }
}
