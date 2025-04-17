package org.uwl.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uwl.entity.Order;
import org.uwl.entity.User;
import org.uwl.repository.OrderRepository;

@Service
public class OrderService {

  @Autowired private OrderRepository orderRepository;
  @Autowired private ProductService productService;

  @Transactional
  public Order save(Order order) {
    order.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    order.getOrderItems().forEach(item -> item.setOrder(order));
    synchronized (this) {
      productService.stockDeduction(order.getOrderItems());
      order.setStatus(Order.Status.Open);
      return orderRepository.save(order);
    }
  }

  @Transactional(readOnly = true)
  public Order findById(Long id) {
    return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional(readOnly = true)
  public List<Order> getOrdersByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }
}
